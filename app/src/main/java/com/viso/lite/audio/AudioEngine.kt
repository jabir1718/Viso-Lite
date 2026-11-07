package com.viso.lite.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.net.Uri

class AudioEngine(private val context: Context) {

    private var player: MediaPlayer? = null
    private var visualizer: Visualizer? = null
    private val analyzer = BandAnalyzer()
    val bands = FloatArray(6)

    fun load(uri: Uri) {
        release()
        val mp = MediaPlayer()
        player = mp
        mp.setDataSource(context, uri)
        mp.isLooping = true
        mp.prepare()
        mp.start()
        val vis = Visualizer(mp.audioSessionId)
        visualizer = vis
        vis.captureSize = Visualizer.getCaptureSizeRange()[1]
        vis.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
            override fun onWaveFormDataCapture(v: Visualizer?, wf: ByteArray?, rate: Int) = Unit
            override fun onFftDataCapture(v: Visualizer?, fft: ByteArray?, rate: Int) {
                analyzer.push(fft, bands)
            }
        }, Visualizer.getMaxCaptureRate() / 2, false, true)
        vis.enabled = true
    }

    fun pause() {
        player?.let { if (it.isPlaying) it.pause() }
        visualizer?.let { if (it.enabled) it.enabled = false }
    }

    fun resume() {
        player?.let { if (!it.isPlaying) it.start() }
        visualizer?.let { if (!it.enabled) it.enabled = true }
    }

    fun setSensitivity(v: Float) { analyzer.sensitivity = v }

    fun release() {
        visualizer?.let { it.enabled = false; it.release() }
        visualizer = null
        player?.release()
        player = null
        bands.fill(0f)
    }
}
