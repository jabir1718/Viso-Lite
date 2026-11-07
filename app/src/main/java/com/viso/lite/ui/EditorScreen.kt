package com.viso.lite.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viso.lite.EditorViewModel
import com.viso.lite.gl.GlPreviewView

@Composable
fun EditorScreen(vm: EditorViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    val audioPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let(vm::setAudio)
    }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let(vm::setImage)
    }

    var preview by remember { mutableStateOf<GlPreviewView?>(null) }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        AndroidView(
            factory = { ctx -> GlPreviewView(ctx).also { preview = it; it.bands = vm.bands } },
            update = { view ->
                view.bands = vm.bands
                state.cut?.let { view.renderer.setMask(it) }
                state.overlay?.let { view.renderer.setImage(it) }
                view.renderer.mode = state.mode
                view.renderer.opacity = state.opacity
            },
            modifier = Modifier.weight(1f).fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { audioPicker.launch(arrayOf("audio/*")) }) { Text("Audio") }
            Button(onClick = { imagePicker.launch(arrayOf("image/*")) }) { Text("PNG") }
            Button(onClick = vm::cutout, enabled = state.image != null) { Text("Remove BG") }
        }

        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Bars", "Ring", "Wave", "Dots", "Mask").forEachIndexed { i, label ->
                OutlinedButton(onClick = { vm.setMode(i) }) {
                    Text(label, color = if (state.mode == i) Color.Cyan else Color.Gray)
                }
            }
        }

        Spacer(Modifier.height(6.dp))
        Text("Sensitivity", color = Color.Gray)
        Slider(value = state.sensitivity, onValueChange = vm::setSensitivity, valueRange = 0.4f..4f)
        Text("Opacity", color = Color.Gray)
        Slider(value = state.opacity, onValueChange = vm::setOpacity)

        if (state.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
    }
}
