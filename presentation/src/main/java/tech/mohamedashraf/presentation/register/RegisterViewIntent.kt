package tech.mohamedashraf.presentation.register

sealed class RegisterViewIntent {
    data class Register(val name: String, val email: String, val password: String, val passwordConfirmation: String) : RegisterViewIntent()
}