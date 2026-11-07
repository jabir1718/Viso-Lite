package com.viso.lite.audio

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

class BandAnalyzer(private val bands: Int = 6) {

    private val env = FloatArray(bands)
    var attack = 0.65f
    var release = 0.12f
    var sensitivity = 1.4f

    fun push(fft: ByteArray?, out: FloatArray) {
        if (fft == null || fft.size < bands * 2) { decay(out); return }
        val n = fft.size / 2
        val per = max(1, n / bands)
        for (b in 0 until bands) {
            var sum = 0f
            var count = 0
            val start = b * per
            val end = minOf(n, start + per)
            var i = start
            while (i < end) {
                sum += hypot(fft[2 * i].toFloat(), fft[2 * i + 1].toFloat())
                count++
                i++
            }
            val target = (if (count == 0) 0f else (sum / count) / 128f * sensitivity).coerceIn(0f, 1f)
            env[b] = if (target > env[b]) env[b] + (target - env[b]) * attack
            else env[b] + (target - env[b]) * release
            out[b] = env[b]
        }
    }

    private fun decay(out: FloatArray) {
        for (b in out.indices) {
            env[b] = abs(env[b] - release * 0.5f)
            out[b] = env[b]
        }
    }
}
