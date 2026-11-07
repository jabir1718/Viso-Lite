package com.viso.lite.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import com.google.mlkit.vision.segmentation.Segmentation
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.ByteOrder
import kotlin.coroutines.resume

object SubjectCutter {
    private val subjectSegmenter by lazy {
        SubjectSegmentation.getClient(
            SubjectSegmenterOptions.Builder()
                .enableForegroundBitmap()
                .enableMultipleSubjects(SubjectSegmenterOptions.SubjectResultOptions.Builder().build())
                .build()
        )
    }
    private val selfieSegmenter by lazy {
        Segmentation.getClient(
            SelfieSegmenterOptions.Builder()
                .setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE)
                .build()
        )
    }

    suspend fun cut(context: Context, uri: Uri): Pair<Bitmap?, String?> {
        val image = try {
            InputImage.fromFilePath(context, uri)
        } catch (error: Exception) {
            return null to (error.message ?: "Image load failed")
        }
        try {
            val result = subjectSegmenter.process(image).awaitResult()
            result.foregroundBitmap?.let { return it to null }
        } catch (_: Exception) {
            // Fall through to the bundled selfie segmentation model.
        }
        return try {
            val mask = selfieSegmenter.process(image).awaitResult()
            val original = context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
                ?: return null to "Image could not be decoded for mask creation."
            val buffer = mask.buffer.order(ByteOrder.nativeOrder())
            val mw = mask.width
            val mh = mask.height
            val pixels = IntArray(original.width * original.height)
            for (y in 0 until original.height) {
                val my = (y.toFloat() / original.height * mh).toInt().coerceIn(0, mh - 1)
                for (x in 0 until original.width) {
                    val mx = (x.toFloat() / original.width * mw).toInt().coerceIn(0, mw - 1)
                    val confidence = buffer.getFloat((my * mw + mx) * 4).coerceIn(0f, 1f)
                    val source = original.getPixel(x, y)
                    pixels[y * original.width + x] = Color.argb(
                        (confidence * 255).toInt(), Color.red(source), Color.green(source), Color.blue(source)
                    )
                }
            }
            Bitmap.createBitmap(pixels, original.width, original.height, Bitmap.Config.ARGB_8888).also {
                if (!original.isRecycled) original.recycle()
            } to null
        } catch (error: Exception) {
            null to "Both background-removal models failed: \${error.message ?: error.javaClass.simpleName}"
        }
    }

    private suspend fun <T> com.google.android.gms.tasks.Task<T>.awaitResult(): T =
        suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { if (continuation.isActive) continuation.resume(it) }
            addOnFailureListener { if (continuation.isActive) continuation.resumeWith(Result.failure(it)) }
            addOnCanceledListener { if (continuation.isActive) continuation.cancel() }
        }
}