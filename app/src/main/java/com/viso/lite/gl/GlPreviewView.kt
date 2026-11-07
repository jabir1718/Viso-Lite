package com.viso.lite.gl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GlPreviewView(context: Context) : GLSurfaceView(context) {

    val renderer = Renderer()
    var bands: FloatArray = FloatArray(6)
    private val start = System.nanoTime()

    init {
        setEGLContextClientVersion(2)
        setRenderer(object : Renderer() {
            override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) { renderer.init() }
            override fun onSurfaceChanged(gl: GL10?, w: Int, h: Int) {
                GLES20.glViewport(0, 0, w, h)
            }
            override fun onDrawFrame(gl: GL10?) {
                val t = (System.nanoTime() - start) / 1_000_000_000f
                renderer.draw(width, height, t, bands)
            }
        })
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}
