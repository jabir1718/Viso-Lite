package com.viso.lite

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.viso.lite.audio.AudioAnalyzer
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
    val aspect: Float = 1f,
    val mode: Int = 0,
    val opacity: Float = 0.85f,
    val sensitivity: Float = 1.4f,
    val overlayScale: Float = 0.75f,
    val overlayY: Float = 0f,
    val vizScale: Float = 1f,
    val hue: Float = 0f,
    val busy: Boolean = false,
    val exporting: Boolean = false,
    val progress: Int = 0,
    val exportError: String? = null,
    val error: String? = null
)

class EditorViewModel(app: Application) : AndroidViewModel(app) {
    private val engine = AudioEngine(app)
    private val analyzer = AudioAnalyzer()
    private val exporter = VideoExporter(app)
    private var exportJob: Job? = null
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    val bands: FloatArray get() = analyzer.at(engine.positionMs())
    val pulse: Float get() = bands.getOrElse(1) { 0f }

    fun durationMs(): Long = analyzer.durationMs

    fun setAudio(uri: Uri) {
        analyzer.clear()
        engine.release()
        _state.value = _state.value.copy(busy = true, audio = null, exportError = null, error = null)
        viewModelScope.launch {
            try {
                val ok = withContext(Dispatchers.Default) {
                    analyzer.analyze(getApplication(), uri, _state.value.sensitivity)
                }
                if (!ok) error("Audio could not be decoded or contains no usable PCM data.")
                withContext(Dispatchers.IO) { engine.play(uri) }
                _state.value = _state.value.copy(audio = uri, busy = false, error = null)
            } catch (error: Exception) {
                analyzer.clear()
                engine.release()
                _state.value = _state.value.copy(
                    audio = null,
                    busy = false,
                    error = error.message ?: "Audio analysis or playback failed"
                )
            }
        }
    }

    fun setImage(uri: Uri) {
        _state.value = _state.value.copy(image = uri, cut = null, overlay = null, aspect = 1f, error = null)
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.IO) { decode(uri) }
            if (bitmap == null) {
                _state.value = _state.value.copy(error = "Image load failed or unsupported image format.")
            } else {
                _state.value = _state.value.copy(
                    overlay = bitmap,
                    aspect = bitmap.width.toFloat() / bitmap.height.toFloat(),
                    error = null
                )
            }
        }
    }

    private fun decode(uri: Uri): Bitmap? {
        val resolver = getApplication<Application>().contentResolver
        val opts = BitmapFactory.Options().apply { inSampleSize = 2 }
        val bitmap = try {
            resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, opts) }
        } catch (_: Exception) {
            null
        } ?: return null

        val orientation = try {
            resolver.openInputStream(uri)?.use { input ->
                ExifInterface(input).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            } ?: ExifInterface.ORIENTATION_NORMAL
        } catch (_: Exception) {
            ExifInterface.ORIENTATION_NORMAL
        }

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.setScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE -> { matrix.setRotate(90f); matrix.postScale(-1f, 1f) }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> { matrix.setRotate(-90f); matrix.postScale(-1f, 1f) }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270f)
        }
        if (orientation == ExifInterface.ORIENTATION_NORMAL || orientation == ExifInterface.ORIENTATION_UNDEFINED) {
            return bitmap
        }
        return try {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                .also { if (it !== bitmap) bitmap.recycle() }
        } catch (_: Exception) {
            bitmap
        }
    }

    fun setMode(mode: Int) { _state.value = _state.value.copy(mode = mode) }
    fun setOpacity(value: Float) { _state.value = _state.value.copy(opacity = value) }
    fun setOverlayScale(value: Float) { _state.value = _state.value.copy(overlayScale = value) }
    fun setOverlayY(value: Float) { _state.value = _state.value.copy(overlayY = value) }
    fun setVizScale(value: Float) { _state.value = _state.value.copy(vizScale = value) }
    fun setHue(value: Float) { _state.value = _state.value.copy(hue = value) }
    fun pause() { engine.pause() }
    fun resume() { engine.resume() }

    fun setSensitivity(value: Float) {
        analyzer.setSensitivity(value)
        _state.value = _state.value.copy(sensitivity = value)
    }

    fun cutout() {
        val uri = _state.value.image ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(busy = true, error = null)
            val result = withContext(Dispatchers.Default) { SubjectCutter.cut(getApplication(), uri) }
            _state.value = _state.value.copy(busy = false, cut = result.first, error = result.second)
        }
    }

    fun clearError() { _state.value = _state.value.copy(error = null) }

    fun export(uri: Uri, mode: Int, outUri: Uri) {
        if (_state.value.exporting) return
        exportJob = viewModelScope.launch {
            _state.value = _state.value.copy(exporting = true, progress = 0, exportError = null)
            try {
                val snapshot = _state.value
                val duration = analyzer.durationMs
                require(duration > 0L) { "Audio analysis duration is unavailable" }
                val file = withContext(Dispatchers.Default) {
                    exporter.export(
                        audio = uri,
                        durationMs = duration,
                        bandsProvider = { frame -> analyzer.frameAt(frame) },
                        mode = mode,
                        overlay = snapshot.overlay,
                        mask = snapshot.cut,
                        opacity = snapshot.opacity,
                        overlayScale = snapshot.overlayScale,
                        overlayOffY = snapshot.overlayY,
                        overlayAspect = snapshot.aspect,
                        vizScale = snapshot.vizScale,
                        hue = snapshot.hue
                    ) { value ->
                        _state.value = _state.value.copy(progress = value.coerceIn(0, 100))
                    }
                }
                if (file == null) {
                    _state.value = _state.value.copy(exporting = false, exportError = "Export was cancelled or produced no file.")
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
        // Keep the UI busy until the encoder has actually stopped and released its resources.
        exporter.cancel()
    }

    override fun onCleared() {
        exporter.cancel()
        engine.release()
        analyzer.clear()
        super.onCleared()
    }
}