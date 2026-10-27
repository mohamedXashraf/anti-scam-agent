package tech.mohamedashraf.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.displayFontFamily
import tech.mohamedashraf.presentation.ui.theme.errorLight
import tech.mohamedashraf.presentation.ui.theme.outlineLight
import tech.mohamedashraf.presentation.ui.theme.primaryLight

@Composable
fun <T> DropDownMenuView(
    modifier: Modifier = Modifier.fillMaxWidth(),
    items: List<T>,
    selectedItem: MutableState<T>,
    icon: ImageVector? = null,
    label: String = "",
    hint: String = "",
    radius: Int = 16,
    enabled: Boolean = true,
    shouldValidateEmptyView: Boolean = false,
    itemLabel: (T) -> String = { it?.toString() ?: "" },
    onItemSelected: (T) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val isError = shouldValidateEmptyView && (selectedItem.value == null || (selectedItem.value is String && (selectedItem.value as String).isBlank()))

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { if (enabled) expanded = !expanded }) {
            OutlinedTextField(
                value = selectedItem.value?.let(itemLabel) ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                isError = isError,
                enabled = enabled,
                shape = RoundedCornerShape(radius.dp),
                label = { Text(text = label, fontFamily = displayFontFamily) },
                placeholder = { Text(text = hint.ifBlank { label }, fontFamily = displayFontFamily) },
                leadingIcon = if (icon != null) { { Icon(icon, contentDescription = "", tint = primaryLight) } } else null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = primaryLight,
                    focusedContainerColor = backgroundLight,
                    unfocusedContainerColor = backgroundLight,
                    unfocusedTextColor = outlineLight,
                    cursorColor = primaryLight,
                    errorCursorColor = errorLight,
                    errorTextColor = errorLight,
                    errorContainerColor = backgroundLight
                )
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = itemLabel(item), fontFamily = displayFontFamily) },
                        onClick = {
                            selectedItem.value = item
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
        if (isError) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                text = stringResource(id = R.string.missing_input_error),
                color = errorLight,
                fontWeight = FontWeight.Medium
            )
        }
    }
}