package com.viso.lite.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

class AudioEngine(private val context: Context) {

    private var player: MediaPlayer? = null
    private var visualizer: Visualizer? = null
    private val analyzer = BandAnalyzer()
    val bands = FloatArray(6)

    private val handler = Handler(Looper.getMainLooper())
    private var visualizerGeneration = 0

    /** Called when the device Visualizer cannot be initialized and fallback is enabled. */
    var onError: ((String) -> Unit)? = null

    private val fallbackRunnable = object : Runnable {
        override fun run() {
            if (player == null || visualizer != null) return
            val t = SystemClock.uptimeMillis() / 1000.0
            for (i in bands.indices) {
                val wave = (sin(t * (2.0 + i * 0.73) * 2.0 * PI) + 1.0) * 0.5
                val noise = Random.nextFloat().toDouble() * 0.22
                bands[i] = (wave * 0.72 + noise).toFloat().coerceIn(0f, 1f)
            }
            handler.postDelayed(this, 60L)
        }
    }

    fun load(uri: Uri) {
        release()
        val mp = MediaPlayer()
        player = mp
        mp.setDataSource(context, uri)
        mp.isLooping = true
        mp.prepare()
        mp.start()

        val generation = ++visualizerGeneration
        // Audio session IDs can become available to audio effects shortly after playback starts.
        handler.postDelayed({ initializeVisualizer(generation, 0) }, 400L)
    }

    private fun initializeVisualizer(generation: Int, retry: Int) {
        if (generation != visualizerGeneration || player == null || visualizer != null) return

        var candidate: Visualizer? = null
        try {
            val mp = player ?: return
            val created = Visualizer(mp.audioSessionId)
            candidate = created

            val range = Visualizer.getCaptureSizeRange()
            require(range.size >= 2 && range[0] > 0 && range[1] >= range[0]) {
                "Visualizer capture-size range is unavailable"
            }
            val minSize = range[0]
            val maxSize = range[1]
            val minPower = Integer.highestOneBit(minSize).let {
                if (it < minSize) it shl 1 else it
            }
            val maxPower = Integer.highestOneBit(maxSize)
            require(maxPower >= minPower) { "Visualizer capture-size range is invalid" }
            created.captureSize = maxPower.coerceIn(minPower, maxPower)

            created.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
                override fun onWaveFormDataCapture(v: Visualizer?, wf: ByteArray?, rate: Int) = Unit

                override fun onFftDataCapture(v: Visualizer?, fft: ByteArray?, rate: Int) {
                    if (generation == visualizerGeneration && fft != null) {
                        analyzer.push(fft, bands)
                    }
                }
            }, Visualizer.getMaxCaptureRate() / 2, false, true)

            created.enabled = true
            visualizer = created
            handler.removeCallbacks(fallbackRunnable)
            return
        } catch (error: Exception) {
            try {
                candidate?.let {
                    if (it.enabled) it.enabled = false
                    it.release()
                }
            } catch (_: Exception) {
                // Ignore cleanup errors while retrying initialization.
            }
            if (retry < 2) {
                handler.postDelayed({ initializeVisualizer(generation, retry + 1) }, 400L)
            } else {
                val message = error.message ?: error.javaClass.simpleName
                onError?.invoke("Visualizer unavailable ($message). Using fallback animation.")
                handler.removeCallbacks(fallbackRunnable)
                handler.post(fallbackRunnable)
            }
        }
    }

    fun pause() {
        player?.let { if (it.isPlaying) it.pause() }
        visualizer?.let {
            try {
                if (it.enabled) it.enabled = false
            } catch (_: Exception) {
                // The effect may already have been released by the system.
            }
        }
        handler.removeCallbacks(fallbackRunnable)
    }

    fun resume() {
        player?.let { if (!it.isPlaying) it.start() }
        visualizer?.let {
            try {
                if (!it.enabled) it.enabled = true
            } catch (error: Exception) {
                onError?.invoke("Visualizer could not resume: ${error.message ?: "unknown error"}")
            }
        } ?: run {
            if (player != null) handler.post(fallbackRunnable)
        }
    }

    fun setSensitivity(v: Float) { analyzer.sensitivity = v }

    fun release() {
        visualizerGeneration++
        handler.removeCallbacksAndMessages(null)
        visualizer?.let {
            try {
                if (it.enabled) it.enabled = false
            } catch (_: Exception) {
                // Continue releasing even if disabling fails.
            }
            try {
                it.release()
            } catch (_: Exception) {
                // Continue releasing the player.
            }
        }
        visualizer = null
        player?.release()
        player = null
        bands.fill(0f)
    }
}
