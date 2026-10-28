package tech.mohamedashraf.presentation.ui.component

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.mohamedashraf.presentation.ui.theme.primaryLight
import tech.mohamedashraf.presentation.ui.theme.surfaceLight

@Composable
fun ButtonView(modifier: Modifier = Modifier, label: String, width: ButtonWidth = ButtonWidth.FILLED, radius: Int = 32, action: () -> Unit)
{
    val finalModifier = when (width){
        ButtonWidth.WRAPPED -> modifier.height(50.dp).wrapContentWidth()
        ButtonWidth.MEDIUM -> modifier.height(50.dp).width(buttonWidth(LocalContext.current).dp)
        ButtonWidth.FILLED -> modifier.height(50.dp).fillMaxWidth()
    }
    Button(modifier = finalModifier,
        shape = RoundedCornerShape(radius.dp),
        colors = ButtonDefaults.buttonColors(containerColor = primaryLight, contentColor = surfaceLight),
        onClick = action
    ) {
        TextView(text = label.uppercase(), size = 16, color = surfaceLight)
    }
}

@Composable
fun TextButtonView(modifier: Modifier = Modifier, label: String, width: ButtonWidth = ButtonWidth.FILLED, action: () -> Unit) {
    val finalModifier = when (width){
        ButtonWidth.WRAPPED -> modifier.wrapContentWidth()
        ButtonWidth.MEDIUM -> modifier.width(buttonWidth(LocalContext.current).dp)
        ButtonWidth.FILLED -> modifier.fillMaxWidth()
    }
    TextButton(onClick = action) {
        TextView(modifier = finalModifier, text = label.uppercase(), size = 13, align = TextAlign.Center)
    }
}

enum class ButtonWidth{
    WRAPPED, FILLED, MEDIUM
}

private fun buttonWidth(context: Context): Double{
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels * 0.255
}