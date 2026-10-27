package tech.mohamedashraf.presentation.splash

import tech.mohamedashraf.presentation.application.Route

sealed class SplashViewState {
    data object Loading : SplashViewState()
    data class Authentification(val destination: Route) : SplashViewState()
    data class Error(val error: Throwable) : SplashViewState()
}