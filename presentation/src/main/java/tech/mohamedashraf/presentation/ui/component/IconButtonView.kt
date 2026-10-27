package tech.mohamedashraf.presentation.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButtonView(icon: ImageVector, tint: Color, action: () -> Unit) {
    IconButton(onClick = action) { Icon(imageVector = icon, tint = tint, contentDescription = "") }
}