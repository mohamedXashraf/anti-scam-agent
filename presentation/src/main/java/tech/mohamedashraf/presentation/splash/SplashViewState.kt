package tech.mohamedashraf.presentation.splash

sealed class SplashViewState {
    data object Loading : SplashViewState()
    data class Authentification(val isAuthenticated: Boolean) : SplashViewState()
    data class Error(val error: Throwable) : SplashViewState()
}