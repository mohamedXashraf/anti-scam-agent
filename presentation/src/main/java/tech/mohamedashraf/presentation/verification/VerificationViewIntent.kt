package tech.mohamedashraf.presentation.verification

sealed class VerificationViewIntent {
    data object CheckUserVerification : VerificationViewIntent()
    data object SendVerificationEmail : VerificationViewIntent()
    data object Logout : VerificationViewIntent()
}