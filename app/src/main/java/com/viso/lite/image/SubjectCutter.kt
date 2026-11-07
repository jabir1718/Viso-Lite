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

    private val segmenter = SubjectSegmentation.getClient(
        SubjectSegmenterOptions.Builder().enableForegroundBitmap().build()
    )

    suspend fun cut(context: Context, uri: Uri): Bitmap? = suspendCancellableCoroutine { cont ->
        val image = InputImage.fromFilePath(context, uri)
        segmenter.process(image)
            .addOnSuccessListener { cont.resume(it.foregroundBitmap) }
            .addOnFailureListener { cont.resume(null) }
    }
}
