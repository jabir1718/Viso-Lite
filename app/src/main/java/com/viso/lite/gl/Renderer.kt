package com.viso.lite.gl

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Renderer {

    private var program = 0
    private var maskTex = 0
    private var imageTex = 0
    private var aPos = 0
    private val quad: FloatBuffer = ByteBuffer
        .allocateDirect(8 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        .apply { put(floatArrayOf(-1f, -1f, 1f, -1f, -1f, 1f, 1f, 1f)); position(0) }

    var mode = 0
    var opacity = 1f

    fun init() {
        program = build(Shaders.VERT, Shaders.FRAG)
        aPos = GLES20.glGetAttribLocation(program, "aPos")
        maskTex = newTex()
        imageTex = newTex()
    }

    private fun build(vs: String, fs: String): Int {
        val p = GLES20.glCreateProgram()
        GLES20.glAttachShader(p, compile(GLES20.GL_VERTEX_SHADER, vs))
        GLES20.glAttachShader(p, compile(GLES20.GL_FRAGMENT_SHADER, fs))
        GLES20.glLinkProgram(p)
        return p
    }

    private fun compile(type: Int, src: String): Int {
        val s = GLES20.glCreateShader(type)
        GLES20.glShaderSource(s, src)
        GLES20.glCompileShader(s)
        return s
    }

    private fun newTex(): Int {
        val t = IntArray(1)
        GLES20.glGenTextures(1, t, 0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, t[0])
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        return t[0]
    }

    fun setMask(bmp: Bitmap) = upload(maskTex, bmp)
    fun setImage(bmp: Bitmap) = upload(imageTex, bmp)

    private fun upload(tex: Int, bmp: Bitmap) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0)
    }

    fun draw(width: Int, height: Int, time: Float, bands: FloatArray) {
        GLES20.glViewport(0, 0, width, height)
        GLES20.glClearColor(0.03f, 0.027f, 0.047f, 1f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glUseProgram(program)

        GLES20.glUniform2f(GLES20.glGetUniformLocation(program, "uRes"), width.toFloat(), height.toFloat())
        GLES20.glUniform1f(GLES20.glGetUniformLocation(program, "uTime"), time)
        GLES20.glUniform1f(GLES20.glGetUniformLocation(program, "uMode"), mode.toFloat())
        GLES20.glUniform1f(GLES20.glGetUniformLocation(program, "uOpacity"), opacity)
        GLES20.glUniform1fv(GLES20.glGetUniformLocation(program, "uBands"), 6, bands, 0)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, maskTex)
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "uMask"), 1)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE2)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imageTex)
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "uImage"), 2)

        quad.position(0)
        GLES20.glEnableVertexAttribArray(aPos)
        GLES20.glVertexAttribPointer(aPos, 2, GLES20.GL_FLOAT, false, 0, quad)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(aPos)
    }
}
