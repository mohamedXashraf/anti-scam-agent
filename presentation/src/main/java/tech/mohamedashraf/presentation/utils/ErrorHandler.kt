package tech.mohamedashraf.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.domain.error.*

@Composable
fun Throwable.errorMessage(): String {
    return when (this) {
        is MissingInfoError -> stringResource(R.string.missing_input_error)
        is NoSalesToDownloadError -> stringResource(R.string.no_sales_to_download_error)
        else -> stringResource(R.string.general_error)
    }
}