package tech.mohamedashraf.presentation.agent

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.IconButtonView
import tech.mohamedashraf.presentation.ui.component.SettingDialog
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.primaryLight
import tech.mohamedashraf.presentation.utils.camera
import tech.mohamedashraf.presentation.utils.requestPermissions
import java.io.File

@Composable
fun Agent(navigate: (Route) -> Unit = {}, productId: String) {
    AgentView()
}

@Composable
private fun AgentView() {

    val context = LocalContext.current
    val video = remember { mutableStateOf<File?>(null) }
    val videoReady = remember { mutableStateOf(false) }
    val showSettingsDialog = remember { mutableStateOf(false) }
    val cameraLauncher = camera { success ->
        videoReady.value = success
        if (!success) {
            video.value?.delete()
            video.value = null
        }
    }
    val launcher = requestPermissions { isGranted ->
        if (isGranted) {
            val file = File(File(context.cacheDir, "videos").apply { mkdirs() }, "${System.currentTimeMillis()}.mp4")
            video.value = file
            cameraLauncher.launch(FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file))
        }
        else {
            val activity = context.findActivity()
            if (activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                showSettingsDialog.value = true
            }
        }
    }

    if (showSettingsDialog.value) {
        SettingDialog(context, showSettingsDialog)
    }

    LazyColumn (modifier = Modifier
        .background(backgroundLight)
        .fillMaxSize()
        .padding(16.dp)) {
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item { IconButtonView(icon = Icons.Rounded.Camera, tint = primaryLight) { launcher.launch(arrayOf(Manifest.permission.CAMERA)) } }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun CameraView() {

}

private fun Context.findActivity(): ComponentActivity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is ComponentActivity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    AntiScamAgentAppTheme {
        AgentView()
    }
}