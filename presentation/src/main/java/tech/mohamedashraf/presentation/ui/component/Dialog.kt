package tech.mohamedashraf.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.ui.theme.primaryLight
import tech.mohamedashraf.presentation.ui.theme.surfaceLight

enum class DialogType {
    SUCCESS, ERROR, WARNING
}

@Composable
fun MessageView(
    onDismiss: () -> Unit = {}, message: String = "", type: DialogType = DialogType.ERROR
) {

    val visible = remember { mutableStateOf(true) }

    if (visible.value) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = surfaceLight, shape = RoundedCornerShape(CornerSize(16.dp))),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(imageVector = icon(type), modifier = Modifier.size(24.dp), colorFilter = ColorFilter.tint(primaryLight), contentDescription = "")
                    TextView(modifier = Modifier.padding(horizontal = 8.dp), text = label(type), size = 16, fontWeight = FontWeight.Bold, align = TextAlign.Center)
                }
                TextView(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), text = message, size = 14, fontWeight = FontWeight.Normal, align = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 0.3.dp)
                TextButtonView(label = stringResource(R.string.ok), action = {
                    visible.value = false
                    onDismiss()
                })
            }
        }
    }
}

private fun icon(type: DialogType): ImageVector {
    return when (type) {
        DialogType.SUCCESS -> Icons.Rounded.CheckCircleOutline
        DialogType.ERROR -> Icons.Rounded.ErrorOutline
        else -> Icons.Rounded.WarningAmber
    }
}

@Composable
private fun label(type: DialogType): String {
    return stringResource(
        when (type) {
            DialogType.SUCCESS -> R.string.success
            DialogType.ERROR -> R.string.error
            else -> R.string.warning
        }
    )
}

@Preview
@Composable
private fun Preview() {
    MessageView(message = "Somehting went wrong! ... please try again later.", type = DialogType.WARNING)
}