package com.viso.lite

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.JavascriptInterface
import android.widget.Toast
import java.io.File
import java.util.concurrent.Executors

class AndroidSave(private val context: Context) {
    private val worker = Executors.newSingleThreadExecutor()

    @JavascriptInterface
    fun saveBase64(fileName: String, mimeType: String, base64: String) {
        worker.execute {
            try {
                val safeName = fileName.substringAfterLast('/').substringAfterLast('\\')
                    .replace(Regex("[^A-Za-z0-9._-]"), "_")
                    .ifBlank { "viso_export" }
                val bytes = android.util.Base64.decode(base64, android.util.Base64.DEFAULT)
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
                    context.contentResolver.openOutputStream(uri, "w")?.use { it.write(bytes) }
                        ?: error("Gallery file could not be written")
                    if (Build.VERSION.SDK_INT >= 29) {
                        context.contentResolver.update(uri, ContentValues().apply {
                            put(MediaStore.MediaColumns.IS_PENDING, 0)
                        }, null, null)
                    }
                    showMessage("Saved to Gallery / Viso")
                } catch (error: Exception) {
                    context.contentResolver.delete(uri, null, null)
                    throw error
                }
            } catch (error: Exception) {
                showMessage("Save failed: ${error.message ?: "unknown error"}")
            }
        }
    }

    private fun showMessage(message: String) {
        android.os.Handler(context.mainLooper).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
