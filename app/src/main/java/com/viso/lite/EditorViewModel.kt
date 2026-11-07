package com.viso.lite

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.viso.lite.audio.AudioEngine
import com.viso.lite.export.VideoExporter
import com.viso.lite.image.SubjectCutter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UiState(
    val audio: Uri? = null,
    val image: Uri? = null,
    val cut: Bitmap? = null,
    val overlay: Bitmap? = null,
    val overlayAspect: Float = 1f,
    val mode: Int = 0,
    val opacity: Float = 0.85f,
    val sensitivity: Float = 1.4f,
    val overlayScale: Float = 0.75f,
    val overlayY: Float = 0f,
    val busy: Boolean = false,
    val exporting: Boolean = false,
    val progress: Int = 0,
    val exportError: String? = null,
    val error: String? = null
)

private data class DecodedImage(val bitmap: Bitmap, val aspect: Float)

class EditorViewModel(app: Application) : AndroidViewModel(app) {
    private val engine = AudioEngine(app)
    private val exporter = VideoExporter(app)
    private var exportJob: Job? = null
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        engine.onError = { message ->
            _state.value = _state.value.copy(error = message)
        }
    }

    val bands: FloatArray get() = engine.bands
    val pulse: Float get() = engine.bands.getOrElse(1) { 0f }

    fun durationMs(): Long {
        val audioUri = _state.value.audio ?: return 0L
        val player = MediaPlayer()
        return try {
            player.setDataSource(getApplication<Application>(), audioUri)
            player.prepare()
            player.duration.toLong()
        } finally {
            player.release()
        }
    }

    fun setAudio(uri: Uri) {
        runCatching { engine.load(uri) }
            .onSuccess { _state.value = _state.value.copy(audio = uri, exportError = null, error = null) }
            .onFailure {
                _state.value = _state.value.copy(error = it.message ?: "Audio load failed")
            }
    }

    fun setImage(uri: Uri) {
        _state.value = _state.value.copy(
            image = uri,
            cut = null,
            overlay = null,
            overlayAspect = 1f,
            error = null
        )
        viewModelScope.launch {
            val decoded = withContext(Dispatchers.IO) { decode(uri) }
            if (decoded == null) {
                _state.value = _state.value.copy(error = "Image load failed or unsupported image format.")
            } else {
                _state.value = _state.value.copy(
                    overlay = decoded.bitmap,
                    overlayAspect = decoded.aspect,
                    error = null
                )
            }
        }
    }

    private fun decode(uri: Uri): DecodedImage? = runCatching {
        val resolver = getApplication<Application>().contentResolver
        val orientation = resolver.openInputStream(uri)?.use { input ->
            ExifInterface(input).getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } ?: ExifInterface.ORIENTATION_NORMAL

        val options = BitmapFactory.Options().apply { inSampleSize = 2 }
        val bitmap = resolver.openInputStream(uri)?.use { input ->
            BitmapFactory.decodeStream(input, null, options)
        } ?: return@runCatching null

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.setScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270f)
        }

        val corrected = if (orientation == ExifInterface.ORIENTATION_NORMAL ||
            orientation == ExifInterface.ORIENTATION_UNDEFINED
        ) {
            bitmap
        } else {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                .also { if (it !== bitmap) bitmap.recycle() }
        }
        DecodedImage(
            bitmap = corrected,
            aspect = corrected.width.toFloat() / corrected.height.toFloat()
        )
    }.getOrNull()

    fun setMode(mode: Int) { _state.value = _state.value.copy(mode = mode) }
    fun setOpacity(value: Float) { _state.value = _state.value.copy(opacity = value) }
    fun setOverlayScale(value: Float) { _state.value = _state.value.copy(overlayScale = value) }
    fun setOverlayY(value: Float) { _state.value = _state.value.copy(overlayY = value) }

    fun pause() { engine.pause() }

    fun resume() { engine.resume() }

    fun setSensitivity(value: Float) {
        engine.setSensitivity(value)
        _state.value = _state.value.copy(sensitivity = value)
    }

    fun clearError() {
        if (_state.value.error != null) {
            _state.value = _state.value.copy(error = null)
        }
    }

    fun cutout() {
        val uri = _state.value.image ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(busy = true, error = null)
            val result = withContext(Dispatchers.Default) {
                SubjectCutter.cut(getApplication(), uri)
            }
            _state.value = _state.value.copy(
                busy = false,
                cut = result.first,
                error = result.second ?: if (result.first == null) {
                    "Background removal failed. Model download karo / internet on karo."
                } else null
            )
        }
    }

    fun export(uri: Uri, mode: Int, outUri: Uri) {
        if (_state.value.exporting) return
        exportJob = viewModelScope.launch {
            _state.value = _state.value.copy(exporting = true, progress = 0, exportError = null)
            try {
                val duration = withContext(Dispatchers.Default) { durationMs() }
                require(duration > 0L) { "Audio duration is unavailable" }
                val file = withContext(Dispatchers.Default) {
                    exporter.export(uri, duration, { engine.bands.copyOf() }, mode) { value ->
                        _state.value = _state.value.copy(progress = value.coerceIn(0, 100))
                    }
                }
                if (file == null) {
                    _state.value = _state.value.copy(exporting = false)
                    return@launch
                }
                withContext(Dispatchers.IO) {
                    val output = getApplication<Application>().contentResolver.openOutputStream(outUri, "w")
                        ?: error("Cannot open selected export destination")
                    output.use { stream -> file.inputStream().use { it.copyTo(stream) } }
                    file.delete()
                }
                _state.value = _state.value.copy(exporting = false, progress = 100)
            } catch (error: Exception) {
                _state.value = _state.value.copy(
                    exporting = false,
                    exportError = error.message ?: "Export failed"
                )
            }
        }
    }

    fun cancel() {
        exporter.cancel()
        _state.value = _state.value.copy(exporting = false)
    }

    fun setProgress(value: Int) {
        _state.value = _state.value.copy(progress = value.coerceIn(0, 100))
    }

    override fun onCleared() {
        exporter.cancel()
        engine.release()
        super.onCleared()
    }
}
