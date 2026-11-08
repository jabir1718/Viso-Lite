package com.viso.lite

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.webkit.JavascriptInterface
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.Executors

class AndroidSave(private val context: Context) {
    private val worker = Executors.newSingleThreadExecutor()
    private var pendingFile: File? = null
    private var pendingOutput: FileOutputStream? = null
    private var pendingName: String = "viso_export"
    private var pendingMime: String = "application/octet-stream"

    @Synchronized
    @JavascriptInterface
    fun beginSave(fileName: String, mimeType: String): Boolean {
        return try {
            cancelPending()
            pendingName = safeName(fileName)
            pendingMime = mimeType.ifBlank { "application/octet-stream" }
            val temp = File.createTempFile("viso_export_", ".tmp", context.cacheDir)
            pendingFile = temp
            pendingOutput = FileOutputStream(temp)
            true
        } catch (error: Exception) {
            showMessage("Export start failed: ${error.message ?: "unknown error"}")
            false
        }
    }

    @Synchronized
    @JavascriptInterface
    fun appendChunk(base64: String): Boolean {
        return try {
            val output = pendingOutput ?: return false
            output.write(Base64.decode(base64, Base64.DEFAULT))
            true
        } catch (_: Exception) {
            cancelPending()
            false
        }
    }

    @Synchronized
    @JavascriptInterface
    fun finishSave(): Boolean {
        val file = pendingFile ?: return false
        val name = pendingName
        val mime = pendingMime
        return try {
            pendingOutput?.flush()
            pendingOutput?.close()
            pendingOutput = null
            pendingFile = null
            worker.execute {
                try {
                    saveFile(file, name, mime)
                    showMessage("Saved to Gallery / Viso")
                } catch (error: Exception) {
                    showMessage("Save failed: ${error.message ?: "unknown error"}")
                } finally {
                    file.delete()
                }
            }
            true
        } catch (error: Exception) {
            cancelPending()
            showMessage("Export finish failed: ${error.message ?: "unknown error"}")
            false
        }
    }

    @Synchronized
    @JavascriptInterface
    fun cancelSave() {
        cancelPending()
    }

    @JavascriptInterface
    fun saveBase64(fileName: String, mimeType: String, base64: String) {
        worker.execute {
            try {
                val bytes = Base64.decode(base64, Base64.DEFAULT)
                val temp = File.createTempFile("viso_export_", ".tmp", context.cacheDir)
                temp.writeBytes(bytes)
                try {
                    saveFile(temp, safeName(fileName), mimeType)
                } finally {
                    temp.delete()
                }
                showMessage("Saved to Gallery / Viso")
            } catch (error: Exception) {
                showMessage("Save failed: ${error.message ?: "unknown error"}")
            }
        }
    }

    private fun saveFile(file: File, safeName: String, mimeType: String) {
        val isVideo = mimeType.startsWith("video/")
        val collection = if (isVideo) MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, safeName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= 29) {
                val folder = if (isVideo) Environment.DIRECTORY_MOVIES else Environment.DIRECTORY_PICTURES
                put(MediaStore.MediaColumns.RELATIVE_PATH, "$folder/Viso")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }
        val uri = context.contentResolver.insert(collection, values)
            ?: error("Gallery file could not be created")
        try {
            context.contentResolver.openOutputStream(uri, "w")?.use { output ->
                FileInputStream(file).use { input -> input.copyTo(output) }
            } ?: error("Gallery file could not be written")
            if (Build.VERSION.SDK_INT >= 29) {
                context.contentResolver.update(uri, ContentValues().apply {
                    put(MediaStore.MediaColumns.IS_PENDING, 0)
                }, null, null)
            }
        } catch (error: Exception) {
            context.contentResolver.delete(uri, null, null)
            throw error
        }
    }

    private fun safeName(fileName: String): String =
        fileName.substringAfterLast('/').replace(Regex("[^A-Za-z0-9._-]"), "_")
            .ifBlank { "viso_export" }

    @Synchronized
    private fun cancelPending() {
        try { pendingOutput?.close() } catch (_: Exception) { }
        pendingOutput = null
        pendingFile?.delete()
        pendingFile = null
    }

    private fun showMessage(message: String) {
        android.os.Handler(context.mainLooper).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
