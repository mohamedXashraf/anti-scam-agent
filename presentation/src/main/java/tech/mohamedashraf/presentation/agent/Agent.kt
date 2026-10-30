package tech.mohamedashraf.presentation.agent

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.DealVerificationNote
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.ButtonView
import tech.mohamedashraf.presentation.ui.component.IconButtonView
import tech.mohamedashraf.presentation.ui.component.Loading
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.component.SettingDialog
import tech.mohamedashraf.presentation.ui.component.TextView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.error
import tech.mohamedashraf.presentation.ui.theme.outlineLight
import tech.mohamedashraf.presentation.ui.theme.primaryLight
import tech.mohamedashraf.presentation.ui.theme.success
import tech.mohamedashraf.presentation.utils.camera
import tech.mohamedashraf.presentation.utils.errorMessage
import tech.mohamedashraf.presentation.utils.requestPermissions
import java.io.File

@Composable
fun Agent(navigate: (Route) -> Unit = {}, productId: String, viewModel: AgentViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = AgentViewState.ViewInitialization)
    val dealVerification = remember { mutableStateOf(DealVerification()) }

    AgentView(
        dealVerification = dealVerification.value,
        onCheck = { viewModel.intent.postValue(AgentViewIntent.StartAgenticCheck(productId, it)) },
    )

    when(val viewState = state.value) {
        is AgentViewState.ViewInitialization -> {}
        is AgentViewState.Loading -> Loading()
        is AgentViewState.Data -> LaunchedEffect(1) { dealVerification.value = viewState.dealVerification }
        is AgentViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun AgentView(dealVerification: DealVerification, onCheck: (File?) -> Unit = {}) {

    val previewVideo = remember { mutableStateOf<File?>(null) }

    LazyColumn (modifier = Modifier.background(backgroundLight).fillMaxSize().padding(16.dp)) {
        item { Spacer(modifier = Modifier.height(48.dp)) }
        item { TextView(text = stringResource(R.string.agent_label), size = 16, fontWeight = FontWeight.Black) }
        item {  }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextView(modifier = Modifier.weight(1f), text = stringResource(R.string.agent_video_label), size = 13, color = outlineLight)
                Spacer(modifier = Modifier.width(16.dp))
                CameraView { previewVideo.value = it }
                Spacer(modifier = Modifier.width(4.dp))
                if(previewVideo.value != null) Icon(modifier = Modifier.size(32.dp), imageVector = Icons.Rounded.CheckCircle, tint = success, contentDescription = "")
            }
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item { ButtonView(label = stringResource(R.string.agent_start_label)) { onCheck(previewVideo.value) } }
        item { Spacer(modifier = Modifier.height(24.dp)) }
        items(dealVerification.notes) { VerificationNoteItem(it) }
        if (dealVerification.recommendation.isNotBlank()) item { VerificationRecommendationItem(dealVerification.recommendation) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun VerificationNoteItem(note: DealVerificationNote) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextView(modifier = Modifier.weight(1f), text = note.note, size = 13, color = outlineLight)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(modifier = Modifier.size(32.dp), imageVector = if (note.accepted) Icons.Rounded.CheckCircle else Icons.Rounded.Cancel, tint = if (note.accepted) success else error, contentDescription = "")
        }
    }
}

@Composable
private fun VerificationRecommendationItem(recommendation: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            TextView(text = stringResource(R.string.agent_recommendation_label), size = 16)
            Spacer(modifier = Modifier.height(8.dp))
            TextView(text = recommendation, size = 14, color = outlineLight)
        }
    }
}

@Composable
private fun CameraView(onVideoReady: (File) -> Unit = {}) {

    val context = LocalContext.current
    val video = remember { mutableStateOf<File?>(null) }
    val showSettingsDialog = remember { mutableStateOf(false) }
    val cameraLauncher = camera { success ->
        if (success && video.value != null && video.value!!.exists()) {
            onVideoReady(video.value!!)
        } else {
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

    IconButtonView(icon = Icons.Rounded.Camera, tint = primaryLight) { launcher.launch(arrayOf(Manifest.permission.CAMERA)) }
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
        AgentView(DealVerification(
            notes = listOf(
                DealVerificationNote("Product in video matches the listing description", true),
                DealVerificationNote("Seller refused to show serial number", false),
            ),
            recommendation = "You Bargain for it it, it is a fair deal."
        ))
    }
}