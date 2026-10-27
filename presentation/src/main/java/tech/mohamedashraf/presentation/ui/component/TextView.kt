package tech.mohamedashraf.presentation.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import tech.mohamedashraf.presentation.ui.theme.AppTypography
import tech.mohamedashraf.presentation.ui.theme.displayFontFamily
import tech.mohamedashraf.presentation.ui.theme.primaryLight

@Composable
fun TextView(modifier: Modifier = Modifier, text: String, size: Int = 24, color: Color = primaryLight, fontWeight: FontWeight = FontWeight.SemiBold, align: TextAlign = TextAlign.Start, maxLines: Int = Int.MAX_VALUE, overflow: TextOverflow = TextOverflow.Ellipsis, isUnderLined: Boolean = false, isLinedThrough: Boolean = false)
{
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        overflow = overflow,
        fontFamily = displayFontFamily,
        style = TextStyle(
            fontFamily = displayFontFamily,
            color = color,
            fontSize = size.sp,
            fontWeight = fontWeight,
            textAlign = align,
            textDecoration = if (isUnderLined) TextDecoration.Underline else if (isLinedThrough) TextDecoration.LineThrough else TextDecoration.None
        )
    )
}