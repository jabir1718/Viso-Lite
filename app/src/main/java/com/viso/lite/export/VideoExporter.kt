package com.viso.lite.export

import android.content.Context
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

    fun export(
        audio: Uri,
        durationMs: Long,
        bandsProvider: () -> FloatArray,
        mode: Int,
        onProgress: (Int) -> Unit
    ): File? {
        val out = File(context.getExternalFilesDir(null), "viso_${System.currentTimeMillis()}.mp4")
        val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height)
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        format.setInteger(MediaFormat.KEY_BIT_RATE, 8_000_000)
        format.setInteger(MediaFormat.KEY_FRAME_RATE, fps)
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2)

        val encoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
        encoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        val input = encoder.createInputSurface()
        encoder.start()

        val egl = EglHelper(input)
        egl.makeCurrent()
        val renderer = Renderer()
        renderer.init()
        renderer.mode = mode

        val muxer = MediaMuxer(out.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        var videoTrack = -1
        var started = false
        val info = MediaCodec.BufferInfo()
        val total = (durationMs / 1000.0 * fps).toInt().coerceAtLeast(1)
        var frame = 0

        while (frame < total) {
            renderer.draw(width, height, frame.toFloat() / fps, bandsProvider())
            egl.swap()
            var idle = false
            while (!idle) {
                val idx = encoder.dequeueOutputBuffer(info, 10_000)
                if (idx == MediaCodec.INFO_TRY_AGAIN_LATER) idle = true
                else if (idx == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    videoTrack = muxer.addTrack(encoder.outputFormat)
                    muxer.start()
                    started = true
                } else if (idx >= 0) {
                    val buf = encoder.getOutputBuffer(idx)
                    if (buf != null) {
                        if (info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) info.size = 0
                        if (info.size > 0 && started) {
                            buf.position(info.offset)
                            buf.limit(info.offset + info.size)
                            muxer.writeSampleData(videoTrack, buf, info)
                        }
                    }
                    encoder.releaseOutputBuffer(idx, false)
                    if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) idle = true
                }
            }
            frame++
            if (frame % 15 == 0) onProgress(frame * 100 / total)
        }

        encoder.signalEndOfInputStream()
        var eos = false
        while (!eos) {
            val idx = encoder.dequeueOutputBuffer(info, 10_000)
            if (idx >= 0) {
                val buf = encoder.getOutputBuffer(idx)
                if (buf != null && info.size > 0 && started) {
                    buf.position(info.offset)
                    buf.limit(info.offset + info.size)
                    muxer.writeSampleData(videoTrack, buf, info)
                }
                encoder.releaseOutputBuffer(idx, false)
                if (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) eos = true
            }
        }

        encoder.stop()
        encoder.release()
        egl.release()
        copyAudio(audio, muxer, durationMs * 1000)
        if (started) muxer.stop()
        muxer.release()
        onProgress(100)
        return out
    }

    private fun copyAudio(src: Uri, muxer: MediaMuxer, durationUs: Long) {
        val ex = MediaExtractor()
        val pfd = context.contentResolver.openFileDescriptor(src, "r") ?: return
        ex.setDataSource(pfd.fileDescriptor)
        var track = -1
        for (i in 0 until ex.trackCount) {
            val mime = ex.getTrackFormat(i).getString(MediaFormat.KEY_MIME)
            if (mime != null && mime.startsWith("audio/")) { track = i; break }
        }
        if (track < 0) { ex.release(); pfd.close(); return }
        val outTrack = muxer.addTrack(ex.getTrackFormat(track))
        ex.selectTrack(track)
        val buf = ByteBuffer.allocate(256 * 1024)
        val info = MediaCodec.BufferInfo()
        var pts = 0L
        while (pts < durationUs) {
            val size = ex.readSampleData(buf, 0)
            if (size < 0) break
            info.offset = 0
            info.size = size
            info.presentationTimeUs = ex.sampleTime
            info.flags = 0
            muxer.writeSampleData(outTrack, buf, info)
            pts = ex.sampleTime
            ex.advance()
        }
        ex.release()
        pfd.close()
    }
}

private class EglHelper(surface: Surface) {
    private val display: EGLDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
    private val ctx: EGLContext
    private val surf: EGLSurface

    init {
        val attrs = intArrayOf(
            EGL14.EGL_RED_SIZE, 8, EGL14.EGL_GREEN_SIZE, 8, EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT, EGL14.EGL_NONE
        )
        val cfg = arrayOfNulls<EGLConfig>(1)
        EGL14.eglChooseConfig(display, attrs, 0, cfg, 0, 1, IntArray(1), 0)
        ctx = EGL14.eglCreateContext(display, cfg[0], EGL14.EGL_NO_CONTEXT,
            intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE), 0)
        surf = EGL14.eglCreateWindowSurface(display, cfg[0], surface, intArrayOf(EGL14.EGL_NONE), 0)
    }

    fun makeCurrent() { EGL14.eglMakeCurrent(display, surf, surf, ctx) }
    fun swap() { EGL14.eglSwapBuffers(display, surf) }
    fun release() {
        EGL14.eglDestroySurface(display, surf)
        EGL14.eglDestroyContext(display, ctx)
        EGL14.eglReleaseThread()
        EGL14.eglTerminate(display)
    }
}
