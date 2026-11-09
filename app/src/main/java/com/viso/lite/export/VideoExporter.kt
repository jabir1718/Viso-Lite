package com.viso.lite.export

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.net.Uri
import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface
import android.opengl.EGLExt
import android.view.Surface
import com.viso.lite.gl.Renderer
import java.io.File
import java.nio.ByteBuffer

class VideoExporter(
    private val context: Context,
    private val width: Int = 1080,
    private val height: Int = 1920,
    private val fps: Int = 30
) {
    @Volatile private var cancelRequested = false
    fun cancel() { cancelRequested = true }

    fun export(
        audio: Uri,
        durationMs: Long,
        bandsProvider: (Int) -> FloatArray,
        mode: Int,
        overlay: Bitmap?,
        mask: Bitmap?,
        opacity: Float,
        overlayScale: Float,
        overlayOffY: Float,
        overlayAspect: Float,
        vizScale: Float,
        hue: Float,
        onProgress: (Int) -> Unit
    ): File? {
        cancelRequested = false
        val out = File(context.getExternalFilesDir(null), "viso_${System.currentTimeMillis()}.mp4")
        var encoder: MediaCodec? = null
        var muxer: MediaMuxer? = null
        var egl: EglHelper? = null
        var extractor: MediaExtractor? = null
        var pfd: android.os.ParcelFileDescriptor? = null
        var muxerStarted = false
        var completed = false
        try {
            val audioFd = context.contentResolver.openFileDescriptor(audio, "r") ?: return null
            pfd = audioFd
            extractor = MediaExtractor()
            extractor.setDataSource(audioFd.fileDescriptor)
            var audioTrack = -1
            for (i in 0 until extractor.trackCount) {
                val mime = extractor.getTrackFormat(i).getString(MediaFormat.KEY_MIME)
                if (mime?.startsWith("audio/") == true) { audioTrack = i; break }
            }
            if (audioTrack < 0) return null
            extractor.selectTrack(audioTrack)
            val audioFormat = extractor.getTrackFormat(audioTrack)

            val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height)
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            format.setInteger(MediaFormat.KEY_BIT_RATE, 8_000_000)
            format.setInteger(MediaFormat.KEY_FRAME_RATE, fps)
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2)
            encoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
            encoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            val input = encoder.createInputSurface()
            encoder.start()
            egl = EglHelper(input).also { it.makeCurrent() }
            muxer = MediaMuxer(out.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
            val renderer = Renderer().also {
                it.init()
                it.mode = mode
                it.opacity = opacity
                it.overlayScale = overlayScale
                it.overlayOffY = overlayOffY
                it.overlayAspect = overlayAspect
                it.vizScale = vizScale
                it.hue = hue
                overlay?.let(it::setImage)
                mask?.let(it::setMask)
            }
            val info = MediaCodec.BufferInfo()
            val totalFrames = (durationMs / 1000.0 * fps).toInt().coerceAtLeast(1)
            var videoTrack = -1
            var outAudioTrack = -1
            var frame = 0
            while (frame < totalFrames && !cancelRequested) {
                renderer.draw(width, height, frame.toFloat() / fps, bandsProvider(frame))
                egl.setPresentationTime(frame * 1_000_000_000L / fps)
                egl.swap()

                var idle = false
                while (!idle && !cancelRequested) {
                    when (val index = encoder.dequeueOutputBuffer(info, 10_000)) {
                        MediaCodec.INFO_TRY_AGAIN_LATER -> idle = true
                        MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                            videoTrack = muxer.addTrack(encoder.outputFormat)
                            outAudioTrack = muxer.addTrack(audioFormat)
                            muxer.start()
                            muxerStarted = true
                        }
                        else -> if (index >= 0) {
                            val buffer = encoder.getOutputBuffer(index)
                            if (buffer != null && info.size > 0 && muxerStarted &&
                                info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG == 0
                            ) {
                                buffer.position(info.offset)
                                buffer.limit(info.offset + info.size)
                                muxer.writeSampleData(videoTrack, buffer, info)
                            }
                            val eos = info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0
                            encoder.releaseOutputBuffer(index, false)
                            if (eos) idle = true
                        }
                    }
                }
                frame++
                if (frame % 10 == 0) onProgress(frame * 90 / totalFrames)
            }
            if (cancelRequested) return null
            encoder.signalEndOfInputStream()
            var eos = false
            while (!eos && !cancelRequested) {
                val index = encoder.dequeueOutputBuffer(info, 10_000)
                if (index >= 0) {
                    val buffer = encoder.getOutputBuffer(index)
                    if (buffer != null && info.size > 0 && muxerStarted &&
                        info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG == 0
                    ) {
                        buffer.position(info.offset)
                        buffer.limit(info.offset + info.size)
                        muxer.writeSampleData(videoTrack, buffer, info)
                    }
                    eos = info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0
                    encoder.releaseOutputBuffer(index, false)
                }
            }
            if (cancelRequested || !muxerStarted) return null

            val audioBuffer = ByteBuffer.allocate(256 * 1024)
            val audioInfo = MediaCodec.BufferInfo()
            val durationUs = durationMs * 1000L
            while (!cancelRequested) {
                audioBuffer.clear()
                val size = extractor.readSampleData(audioBuffer, 0)
                if (size < 0) break
                val pts = extractor.sampleTime
                if (pts < 0 || pts > durationUs) break
                audioInfo.offset = 0
                audioInfo.size = size
                audioInfo.presentationTimeUs = pts
                audioInfo.flags = if (extractor.sampleFlags and MediaExtractor.SAMPLE_FLAG_SYNC != 0)
                    MediaCodec.BUFFER_FLAG_KEY_FRAME else 0
                muxer.writeSampleData(outAudioTrack, audioBuffer, audioInfo)
                extractor.advance()
            }
            if (cancelRequested) return null
            onProgress(100)
            completed = true
            return out
        } catch (error: Exception) {
            out.delete()
            throw error
        } finally {
            runCatching { encoder?.stop() }
            runCatching { encoder?.release() }
            runCatching { egl?.release() }
            runCatching { extractor?.release() }
            runCatching { pfd?.close() }
            runCatching { if (muxerStarted) muxer?.stop() }
            runCatching { muxer?.release() }
            if (!completed) out.delete()
        }
    }
}

private class EglHelper(surface: Surface) {
    private val display: EGLDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
    private val context: EGLContext
    private val eglSurface: EGLSurface

    init {
        val version = IntArray(2)
        check(EGL14.eglInitialize(display, version, 0, version, 1))
        val attributes = intArrayOf(
            EGL14.EGL_RED_SIZE, 8, EGL14.EGL_GREEN_SIZE, 8, EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT, EGL14.EGL_NONE
        )
        val configs = arrayOfNulls<EGLConfig>(1)
        val count = IntArray(1)
        check(EGL14.eglChooseConfig(display, attributes, 0, configs, 0, 1, count, 0))
        val config = checkNotNull(configs[0])
        context = EGL14.eglCreateContext(
            display, config, EGL14.EGL_NO_CONTEXT,
            intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE), 0
        )
        eglSurface = EGL14.eglCreateWindowSurface(display, config, surface, intArrayOf(EGL14.EGL_NONE), 0)
        check(context != EGL14.EGL_NO_CONTEXT && eglSurface != EGL14.EGL_NO_SURFACE)
    }

    fun makeCurrent() { check(EGL14.eglMakeCurrent(display, eglSurface, eglSurface, context)) }
    fun setPresentationTime(nanos: Long) { check(EGLExt.eglPresentationTimeANDROID(display, eglSurface, nanos)) }
    fun swap() { check(EGL14.eglSwapBuffers(display, eglSurface)) }
    fun release() {
        EGL14.eglDestroySurface(display, eglSurface)
        EGL14.eglDestroyContext(display, context)
        EGL14.eglReleaseThread()
        EGL14.eglTerminate(display)
    }
}