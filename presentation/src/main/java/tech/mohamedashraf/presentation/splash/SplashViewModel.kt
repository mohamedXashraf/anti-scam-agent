package tech.mohamedashraf.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.CheckAuthenticatedUser
import tech.mohamedashraf.presentation.application.BaseViewModel
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.login.LoginViewState
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
            val user = checkAuthenticatedUser()
            if (user != null) {
                state.postValue(SplashViewState.Authentification(if (user.verified) Route.BASE_HOME else Route.VERIFICATION))
            } else state.postValue(SplashViewState.Authentification(Route.BASE_LOGIN))
        } catch (error: Exception) {
            state.postValue(SplashViewState.Error(error))
        }
    }
}