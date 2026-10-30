package tech.mohamedashraf.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.domain.error.*

@Composable
fun Throwable.errorMessage(): String {
    return when (this) {
        is InvalidEmailError -> stringResource(R.string.invalid_email_error)
        is InvalidCredentialsError -> stringResource(R.string.invalid_credentials_error)
        is AuthenticationFailedError -> stringResource(R.string.auth_error)
        is MissingInfoError -> stringResource(R.string.missing_input_error)
        is AccountCollisionError -> stringResource(R.string.account_collision_error)
        is AccountNotFoundError -> stringResource(R.string.account_not_found_error)
        is PasswordConfirmationMismatchError -> stringResource(R.string.account_not_found_error)
        is ProductNotFoundError -> stringResource(R.string.invalid_product_error)
        else -> stringResource(R.string.general_error)
    }
}