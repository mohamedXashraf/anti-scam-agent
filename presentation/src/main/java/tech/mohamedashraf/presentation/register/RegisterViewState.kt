package tech.mohamedashraf.presentation.register

import tech.mohamedashraf.presentation.application.Route

sealed class RegisterViewState {
    data object ViewInitialization : RegisterViewState()
    data object Loading : RegisterViewState()
    data class Authentification(val destination: Route) : RegisterViewState()
    data class Error(val error: Throwable) : RegisterViewState()
}