package com.viso.lite.audio

import android.content.Context
import android.media.AudioFormat
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import java.nio.ByteOrder
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.ln
import kotlin.math.sin

/** Decodes audio to mono PCM and caches synchronized FFT bands for preview/export. */
class AudioAnalyzer {
    companion object {
        const val FRAME_MS = 33
        const val BANDS = 6
        const val FFT = 1024
        private const val TARGET_RATE = 44100
    }
    @Volatile private var data: Array<FloatArray> = emptyArray()
    @Volatile var durationMs: Long = 0L
        private set
    @Volatile private var gain = 1f
    @Volatile private var analyzedSensitivity = 1.4f

    fun analyze(context: Context, uri: Uri, sensitivity: Float): Boolean {
        val decoded = decode(context, uri) ?: return false
        val pcm = if (decoded.second == TARGET_RATE) decoded.first else resample(decoded.first, decoded.second, TARGET_RATE)
        if (pcm.isEmpty()) return false
        durationMs = pcm.size * 1000L / TARGET_RATE
        analyzedSensitivity = sensitivity.coerceAtLeast(0.01f)
        gain = 1f
        val out = ArrayList<FloatArray>()
        val env = FloatArray(BANDS)
        val hop = TARGET_RATE * FRAME_MS / 1000
        var pos = 0
        while (pos < pcm.size) {
            val spec = fft(pcm, pos)
            val row = FloatArray(BANDS)
            for (b in 0 until BANDS) {
                val from = (FFT / 2.0 * ln(1 + b) / ln(BANDS.toDouble())).toInt()
                val to = (FFT / 2.0 * ln(2 + b) / ln(BANDS.toDouble())).toInt().coerceAtLeast(from + 1)
                var sum = 0f
                val end = to.coerceAtMost(FFT / 2)
                for (i in from until end) sum += spec[i]
                val count = (end - from).coerceAtLeast(1)
                val target = ((sum / count) * analyzedSensitivity).coerceIn(0f, 1f)
                env[b] = if (target > env[b]) env[b] + (target - env[b]) * 0.65f
                else env[b] + (target - env[b]) * 0.12f
                row[b] = env[b]
            }
            out.add(row)
            pos += hop
        }
        data = out.toTypedArray()
        return data.isNotEmpty()
    }

    fun setSensitivity(value: Float) {
        gain = value.coerceAtLeast(0f) / analyzedSensitivity.coerceAtLeast(0.01f)
    }

    fun at(timeMs: Long): FloatArray {
        val frames = data
        if (frames.isEmpty()) return FloatArray(BANDS)
        return frames[(timeMs.coerceAtLeast(0L) / FRAME_MS).toInt().coerceIn(0, frames.lastIndex)]
            .map { (it * gain).coerceIn(0f, 1f) }.toFloatArray()
    }

    fun frameAt(index: Int): FloatArray {
        val frames = data
        if (frames.isEmpty()) return FloatArray(BANDS)
        return frames[index.coerceIn(0, frames.lastIndex)]
            .map { (it * gain).coerceIn(0f, 1f) }.toFloatArray()
    }

    fun clear() {
        data = emptyArray()
        durationMs = 0L
        gain = 1f
        analyzedSensitivity = 1.4f
    }

    private fun decode(context: Context, uri: Uri): Pair<FloatArray, Int>? {
        val pfd = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val extractor = MediaExtractor()
        var codec: MediaCodec? = null
        try {
            extractor.setDataSource(pfd.fileDescriptor)
            var track = -1
            for (i in 0 until extractor.trackCount) {
                val mime = extractor.getTrackFormat(i).getString(MediaFormat.KEY_MIME)
                if (mime?.startsWith("audio/") == true) { track = i; break }
            }
            if (track < 0) return null
            val inputFormat = extractor.getTrackFormat(track)
            val mime = inputFormat.getString(MediaFormat.KEY_MIME) ?: return null
            val inputRate = inputFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)
            val inputChannels = inputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
            extractor.selectTrack(track)
            codec = MediaCodec.createDecoderByType(mime)
            codec.configure(inputFormat, null, null, 0)
            codec.start()

            val samples = ArrayList<Float>()
            val info = MediaCodec.BufferInfo()
            var inputEos = false
            var outputEos = false
            var outputRate = inputRate
            var outputChannels = inputChannels
            var pcmEncoding = AudioFormat.ENCODING_PCM_16BIT
            var idleLoops = 0
            while (!outputEos && idleLoops < 1000) {
                if (!inputEos) {
                    val inputIndex = codec.dequeueInputBuffer(10_000)
                    if (inputIndex >= 0) {
                        val inputBuffer = codec.getInputBuffer(inputIndex) ?: return null
                        inputBuffer.clear()
                        val size = extractor.readSampleData(inputBuffer, 0)
                        if (size < 0) {
                            codec.queueInputBuffer(inputIndex, 0, 0, 0L, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                            inputEos = true
                        } else {
                            codec.queueInputBuffer(inputIndex, 0, size, extractor.sampleTime, 0)
                            extractor.advance()
                        }
                    }
                }
                when (val outputIndex = codec.dequeueOutputBuffer(info, 10_000)) {
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        val outputFormat = codec.outputFormat
                        outputRate = outputFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)
                        outputChannels = outputFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)
                        pcmEncoding = if (outputFormat.containsKey(MediaFormat.KEY_PCM_ENCODING))
                            outputFormat.getInteger(MediaFormat.KEY_PCM_ENCODING) else AudioFormat.ENCODING_PCM_16BIT
                    }
                    MediaCodec.INFO_TRY_AGAIN_LATER -> idleLoops++
                    else -> if (outputIndex >= 0) {
                        idleLoops = 0
                        val buffer = codec.getOutputBuffer(outputIndex)
                        if (buffer != null && info.size > 0) {
                            buffer.position(info.offset)
                            buffer.limit(info.offset + info.size)
                            val channels = outputChannels.coerceAtLeast(1)
                            if (pcmEncoding == AudioFormat.ENCODING_PCM_FLOAT) {
                                val floats = buffer.order(ByteOrder.nativeOrder()).asFloatBuffer()
                                while (floats.remaining() >= channels) {
                                    var mono = 0f
                                    repeat(channels) { mono += floats.get() }
                                    samples.add(mono / channels)
                                }
                            } else {
                                val shorts = buffer.order(ByteOrder.nativeOrder()).asShortBuffer()
                                while (shorts.remaining() >= channels) {
                                    var mono = 0f
                                    repeat(channels) { mono += shorts.get() / 32768f }
                                    samples.add(mono / channels)
                                }
                            }
                        }
                        codec.releaseOutputBuffer(outputIndex, false)
                        if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) outputEos = true
                    }
                }
            }
            if (!outputEos || samples.isEmpty()) return null
            return samples.toFloatArray() to outputRate
        } finally {
            runCatching { codec?.stop() }
            runCatching { codec?.release() }
            runCatching { extractor.release() }
            runCatching { pfd.close() }
        }
    }

    private fun resample(input: FloatArray, sourceRate: Int, targetRate: Int): FloatArray {
        if (input.isEmpty() || sourceRate <= 0) return FloatArray(0)
        val size = (input.size.toLong() * targetRate / sourceRate).coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
        return FloatArray(size) { i ->
            val source = i.toDouble() * sourceRate / targetRate
            val left = source.toInt().coerceIn(0, input.lastIndex)
            val right = (left + 1).coerceAtMost(input.lastIndex)
            val fraction = (source - left).toFloat()
            input[left] * (1f - fraction) + input[right] * fraction
        }
    }

    private fun fft(samples: FloatArray, offset: Int): FloatArray {
        val n = FFT
        val re = FloatArray(n) { if (offset + it < samples.size) samples[offset + it] else 0f }
        val im = FloatArray(n)
        var j = 0
        for (i in 1 until n) {
            var bit = n shr 1
            while (j and bit != 0) { j = j xor bit; bit = bit shr 1 }
            j = j xor bit
            if (i < j) { val t = re[i]; re[i] = re[j]; re[j] = t }
        }
        var len = 2
        while (len <= n) {
            val ang = -2.0 * PI / len
            var i = 0
            while (i < n) {
                for (k in 0 until len / 2) {
                    val wr = cos(ang * k).toFloat()
                    val wi = sin(ang * k).toFloat()
                    val ur = re[i + k]
                    val ui = im[i + k]
                    val vr = re[i + k + len / 2] * wr - im[i + k + len / 2] * wi
                    val vi = re[i + k + len / 2] * wi + im[i + k + len / 2] * wr
                    re[i + k] = ur + vr
                    im[i + k] = ui + vi
                    re[i + k + len / 2] = ur - vr
                    im[i + k + len / 2] = ui - vi
                }
                i += len
            }
            len = len shl 1
        }
        return FloatArray(n / 2) { hypot(re[it], im[it]) / (n / 4f) }
    }
}