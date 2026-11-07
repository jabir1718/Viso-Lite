package com.viso.lite.ui

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color.Companion.Red
import kotlinx.coroutines.delay
import com.viso.lite.EditorViewModel
import com.viso.lite.gl.GlPreviewView

@Composable
fun EditorScreen(vm: EditorViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var pendingPicker by remember { mutableStateOf<String?>(null) }
    var exportSource by remember { mutableStateOf<Uri?>(null) }
    var galleryHandled by remember { mutableStateOf(false) }
    var permissionMessage by remember { mutableStateOf<String?>(null) }

    val audioPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            vm.setAudio(uri)
        }
    }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            vm.setImage(uri)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result.values.all { it }
        val requested = pendingPicker
        pendingPicker = null
        if (granted && requested == "audio") audioPicker.launch(arrayOf("audio/*"))
        else if (granted && requested == "image") imagePicker.launch(arrayOf("image/*"))
        else if (!granted) permissionMessage = "Media permission is required to open files."
    }
    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("video/mp4")
    ) { uri ->
        if (uri != null) {
            exportSource = uri
            galleryHandled = false
            val audio = state.audio
            if (audio != null) vm.export(audio, state.mode, uri)
        }
    }

    fun requiredPermissions(): Array<String> =
        if (Build.VERSION.SDK_INT >= 33) arrayOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES
        ) else arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    fun openPicker(kind: String) {
        val permissions = requiredPermissions()
        val missing = permissions.any {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
        if (missing) {
            pendingPicker = kind
            permissionLauncher.launch(permissions)
        } else if (kind == "audio") audioPicker.launch(arrayOf("audio/*"))
        else imagePicker.launch(arrayOf("image/*"))
    }

    DisposableEffect(lifecycleOwner, vm) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> vm.pause()
                Lifecycle.Event.ON_RESUME -> vm.resume()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(Unit) {
        val permissions = requiredPermissions()
        if (permissions.any {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }) permissionLauncher.launch(permissions)
    }

    LaunchedEffect(state.error) {
        if (!state.error.isNullOrBlank()) {
            delay(5000L)
            vm.clearError()
        }
    }

    LaunchedEffect(state.exporting, state.progress, exportSource) {
        val source = exportSource
        if (!state.exporting && state.progress == 100 && source != null && !galleryHandled) {
            galleryHandled = true
            runCatching {
                val values = ContentValues().apply {
                    put(MediaStore.Video.Media.DISPLAY_NAME, "Viso_${System.currentTimeMillis()}.mp4")
                    put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                    if (Build.VERSION.SDK_INT >= 29) {
                        put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/Viso")
                        put(MediaStore.Video.Media.IS_PENDING, 1)
                    }
                }
                val galleryUri = context.contentResolver.insert(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values
                ) ?: error("Could not create Gallery video")
                try {
                    val input = context.contentResolver.openInputStream(source)
                        ?: error("Could not read exported video")
                    val output = context.contentResolver.openOutputStream(galleryUri, "w")
                        ?: error("Could not write Gallery video")
                    input.use { inStream -> output.use { outStream -> inStream.copyTo(outStream) } }
                    if (Build.VERSION.SDK_INT >= 29) {
                        context.contentResolver.update(
                            galleryUri, ContentValues().apply {
                                put(MediaStore.Video.Media.IS_PENDING, 0)
                            }, null, null
                        )
                    }
                    val share = Intent(Intent.ACTION_SEND).apply {
                        type = "video/mp4"
                        putExtra(Intent.EXTRA_STREAM, galleryUri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(share, "Share video"))
                } catch (error: Exception) {
                    context.contentResolver.delete(galleryUri, null, null)
                    throw error
                }
            }.onFailure {
                permissionMessage = it.message ?: "Gallery save failed"
            }
        }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var uploadedCut by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
        var uploadedImage by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
        Box(Modifier.aspectRatio(9f / 16f).fillMaxWidth()) {
            AndroidView(
                factory = { ctx -> GlPreviewView(ctx).also { it.bands = vm.bands } },
                update = { view ->
                    view.bands = vm.bands
                    if (uploadedCut !== state.cut) {
                        state.cut?.let(view.renderer::setMask)
                        uploadedCut = state.cut
                    }
                    if (uploadedImage !== state.overlay) {
                        state.overlay?.let(view.renderer::setImage)
                        uploadedImage = state.overlay
                    }
                    view.renderer.mode = state.mode
                    view.renderer.opacity = state.opacity
                    view.renderer.overlayScale = state.overlayScale
                    view.renderer.overlayOffY = state.overlayY
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { openPicker("audio") }) { Text("Audio") }
            Button(onClick = { openPicker("image") }) { Text("PNG") }
            Button(onClick = vm::cutout, enabled = state.image != null && !state.busy) {
                Text("Remove BG")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf("Bars", "Ring", "Wave", "Dots", "Mask").forEachIndexed { index, label ->
                OutlinedButton(
                    onClick = { vm.setMode(index) },
                    enabled = index != 4 || state.cut != null
                ) {
                    Text(label, color = if (state.mode == index) Color.Cyan else Color.Gray)
                }
            }
        }

        Text("Sensitivity")
        Slider(state.sensitivity, vm::setSensitivity, valueRange = 0.4f..4f)
        state.error?.let { error ->
            if (error.isNotBlank()) Text(error, color = Red)
        }
        Text("Opacity")
        Slider(state.opacity, vm::setOpacity, valueRange = 0f..1f)
        Text("Overlay scale")
        Slider(state.overlayScale, vm::setOverlayScale, valueRange = 0.5f..2f)
        Text("Overlay Y")
        Slider(state.overlayY, vm::setOverlayY, valueRange = -0.5f..0.5f)

        Button(
            onClick = { exportLauncher.launch("Viso_Visualiser.mp4") },
            enabled = state.audio != null && state.image != null && !state.exporting
        ) { Text("Export MP4") }

        if (state.exporting) {
            LinearProgressIndicator(
                progress = { state.progress / 100f },
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Exporting ${state.progress}%")
                OutlinedButton(onClick = vm::cancel) { Text("Cancel") }
            }
        }
        if (state.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
        state.exportError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        permissionMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}
