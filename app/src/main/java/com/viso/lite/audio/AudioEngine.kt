package com.viso.lite.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioEngine(private val context: Context) {
    private var player: MediaPlayer? = null

    fun play(uri: Uri) {
        release()
        val mp = MediaPlayer()
        player = mp
        try {
            mp.setDataSource(context, uri)
            mp.isLooping = false
            mp.prepare()
            mp.start()
        } catch (error: Exception) {
            release()
            throw error
        }
    }

    fun durationMs(): Long = runCatching { player?.duration?.toLong() ?: 0L }.getOrDefault(0L)
    fun positionMs(): Long = runCatching { player?.currentPosition?.toLong() ?: 0L }.getOrDefault(0L)
    fun pause() { player?.let { if (it.isPlaying) it.pause() } }
    fun resume() { player?.let { if (!it.isPlaying && it.currentPosition < it.duration) it.start() } }
    fun release() {
        runCatching { player?.stop() }
        runCatching { player?.release() }
        player = null
    }
}