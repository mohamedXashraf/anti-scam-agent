package tech.mohamedashraf.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.CheckAuthenticatedUser
import tech.mohamedashraf.presentation.application.BaseViewModel
import kotlin.time.Duration.Companion.seconds

class SplashViewModel(private val checkAuthenticatedUser: CheckAuthenticatedUser) : BaseViewModel<SplashViewState, SplashViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is SplashViewIntent.CheckUser -> checkUser()
            }
        }
    }

    private fun checkUser() = viewModelScope.launch(Dispatchers.IO) {
        try {
            delay(2.seconds)
            state.postValue(SplashViewState.Authentification(isAuthenticated = checkAuthenticatedUser()))
        } catch (error: Exception) {
            state.postValue(SplashViewState.Error(error))
        }
    }
}