package tech.mohamedashraf.presentation.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun camera(listener: (Boolean) -> Unit) = rememberLauncherForActivityResult(contract = ActivityResultContracts.CaptureVideo() ) { listener(it) }
