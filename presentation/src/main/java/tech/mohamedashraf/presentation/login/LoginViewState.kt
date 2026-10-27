package tech.mohamedashraf.presentation.login

import tech.mohamedashraf.presentation.application.Route

sealed class LoginViewState {
    data object ViewInitialization : LoginViewState()
    data object Loading : LoginViewState()
    data class Authentification(val destination: Route) : LoginViewState()
    data class Error(val error: Throwable) : LoginViewState()
}