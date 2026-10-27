package tech.mohamedashraf.presentation.login

sealed class LoginViewIntent {
    data class Login(val email: String, val password: String) : LoginViewIntent()
}