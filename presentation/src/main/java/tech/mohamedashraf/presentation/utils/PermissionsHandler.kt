package tech.mohamedashraf.presentation.utils

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun requestPermissions(listener: (Boolean) -> Unit): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
{
    return rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
    {
        var isGranted = true
        it.values.forEach { result ->
            isGranted = isGranted && result
        }
        if (it.values.isNotEmpty()) listener(isGranted)
    }
}