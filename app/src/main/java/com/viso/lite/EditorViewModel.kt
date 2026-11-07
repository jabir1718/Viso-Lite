package com.viso.lite

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.viso.lite.audio.AudioEngine
import com.viso.lite.image.SubjectCutter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UiState(
    val audio: Uri? = null,
    val image: Uri? = null,
    val cut: Bitmap? = null,
    val overlay: Bitmap? = null,
    val mode: Int = 0,
    val opacity: Float = 1f,
    val sensitivity: Float = 1.4f,
    val busy: Boolean = false,
    val progress: Int = 0
)

class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val engine = AudioEngine(app)
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    val bands: FloatArray get() = engine.bands

    fun setAudio(uri: Uri) {
        runCatching { engine.load(uri) }
        _state.value = _state.value.copy(audio = uri)
    }

    fun setImage(uri: Uri) {
        _state.value = _state.value.copy(image = uri, cut = null, overlay = null)
        viewModelScope.launch {
            val bmp = withContext(Dispatchers.IO) { decode(uri) }
            _state.value = _state.value.copy(overlay = bmp)
        }
    }

    private fun decode(uri: Uri): Bitmap? = runCatching {
        val opts = BitmapFactory.Options().apply { inSampleSize = 2 }
        getApplication<Application>().contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, opts)
        }
    }.getOrNull()

    fun setMode(mode: Int) { _state.value = _state.value.copy(mode = mode) }
    fun setOpacity(v: Float) { _state.value = _state.value.copy(opacity = v) }

    fun setSensitivity(v: Float) {
        engine.setSensitivity(v)
        _state.value = _state.value.copy(sensitivity = v)
    }

    fun cutout() {
        val uri = _state.value.image ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(busy = true)
            val bmp = withContext(Dispatchers.Default) {
                SubjectCutter.cut(getApplication(), uri)
            }
            _state.value = _state.value.copy(busy = false, cut = bmp)
        }
    }

    fun setProgress(p: Int) { _state.value = _state.value.copy(progress = p) }

    override fun onCleared() {
        engine.release()
        super.onCleared()
    }
}
