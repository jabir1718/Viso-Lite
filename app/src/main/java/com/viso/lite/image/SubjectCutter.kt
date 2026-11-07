package com.viso.lite.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object SubjectCutter {

    private val segmenter by lazy {
        SubjectSegmentation.getClient(
            SubjectSegmenterOptions.Builder()
                .enableForegroundBitmap()
                .enableMultipleSubjects(
                    SubjectSegmenterOptions.MultipleSubjectsOptions.Builder().build()
                )
                .build()
        )
    }

    suspend fun cut(context: Context, uri: Uri): Pair<Bitmap?, String?> {
        val image = try {
            InputImage.fromFilePath(context, uri)
        } catch (error: Exception) {
            return null to (error.message ?: "Image load failed")
        }

        var lastError: Exception? = null
        repeat(2) { attempt ->
            try {
                val bitmap = processImage(image)
                if (bitmap != null) return bitmap to null
                lastError = IllegalStateException("Background removal returned an empty image")
            } catch (error: Exception) {
                lastError = error
            }

            // A second attempt gives ML Kit's on-device model another chance to initialize.
            if (attempt == 0) {
                kotlinx.coroutines.delay(500L)
            }
        }

        val detail = lastError?.message
        return null to if (detail.isNullOrBlank()) {
            "Background removal failed. Model download karo / internet on karo."
        } else {
            "Background removal failed: $detail. Model download karo / internet on karo."
        }
    }

    private suspend fun processImage(image: InputImage): Bitmap? =
        suspendCancellableCoroutine { continuation ->
            try {
                segmenter.process(image)
                    .addOnSuccessListener { result ->
                        if (continuation.isActive) {
                            val bitmap = result.foregroundBitmap
                            continuation.resume(bitmap)
                        }
                    }
                    .addOnFailureListener { error ->
                        if (continuation.isActive) {
                            continuation.resumeWith(Result.failure(error))
                        }
                    }
            } catch (error: Exception) {
                if (continuation.isActive) {
                    continuation.resumeWith(Result.failure(error))
                }
            }
        }
}
