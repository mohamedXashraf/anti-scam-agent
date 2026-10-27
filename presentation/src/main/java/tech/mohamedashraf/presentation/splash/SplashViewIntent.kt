package tech.mohamedashraf.presentation.splash

sealed class SplashViewIntent {
    data object CheckUser : SplashViewIntent()
}