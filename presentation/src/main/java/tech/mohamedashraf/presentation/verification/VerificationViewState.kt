package tech.mohamedashraf.presentation.verification

sealed class VerificationViewState {
    data object ViewInitialization : VerificationViewState()
    data object Loading : VerificationViewState()
    data object Verified : VerificationViewState()
    data object UnVerified : VerificationViewState()
    data object VerificationEmailSent : VerificationViewState()
    data object LoggedOut : VerificationViewState()
    data class Error(val error: Throwable) : VerificationViewState()
}