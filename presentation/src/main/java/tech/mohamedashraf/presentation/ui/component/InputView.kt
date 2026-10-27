package tech.mohamedashraf.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.displayFontFamily
import tech.mohamedashraf.presentation.ui.theme.errorLight
import tech.mohamedashraf.presentation.ui.theme.outlineLight
import tech.mohamedashraf.presentation.ui.theme.primaryLight

@Composable
fun InputView(modifier: Modifier = Modifier.fillMaxWidth(), input: MutableState<String>, keyboardType: KeyboardType, keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences, icon: ImageVector? = null, label: String = "", hint: String = "", maxLines: Int = 1, maxLength: Int = -1, radius: Int = 16, enabled: Boolean = true, shouldClearFocus: Boolean = false, shouldValidateEmptyView: Boolean = false, onChange: (String) -> Unit = {})
{
    val isPassword = keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword
    val passwordIsVisible = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    if (shouldClearFocus) focusManager.clearFocus()

    Column(modifier = modifier) {
        OutlinedTextField(input.value,
            modifier = if (maxLines > 1) Modifier.fillMaxWidth().height(150.dp) else Modifier.fillMaxWidth(),
            visualTransformation = if(isPassword && !passwordIsVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                capitalization = keyboardCapitalization,
                keyboardType = keyboardType
            ),
            isError = shouldValidateEmptyView && input.value.isBlank(),
            enabled = enabled,
            shape = RoundedCornerShape(radius.dp),
            label = { Text(text = label, fontFamily = displayFontFamily) },
            placeholder = { Text(text = hint.ifBlank { label }, fontFamily = displayFontFamily) },
            leadingIcon = if (icon != null) { { Icon(icon, contentDescription = "", tint = primaryLight) } } else null,
            trailingIcon = if (isPassword) { {
                IconButton(modifier = Modifier, onClick = { passwordIsVisible.value = !passwordIsVisible.value }) {
                    Image(imageVector = if (passwordIsVisible.value) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, colorFilter = ColorFilter.tint(color = primaryLight), contentDescription = "")
                }
            } } else null,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            colors = TextFieldDefaults.colors(focusedTextColor = primaryLight, focusedContainerColor = backgroundLight, unfocusedContainerColor = backgroundLight, unfocusedTextColor = outlineLight, cursorColor = primaryLight, errorCursorColor = errorLight, errorTextColor = errorLight, errorContainerColor = backgroundLight),
            onValueChange = {
                if (maxLength != -1 && it.length > maxLength) return@OutlinedTextField
                input.value = it
                onChange(it)
            }
        )
        if (shouldValidateEmptyView && input.value.isBlank()) {
            Text(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), text = stringResource(id = R.string.missing_input_error), color = errorLight, fontWeight = FontWeight.Medium, fontFamily = displayFontFamily)
        }
    }
}