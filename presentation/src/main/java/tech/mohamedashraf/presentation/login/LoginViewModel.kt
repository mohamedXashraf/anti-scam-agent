package tech.mohamedashraf.presentation.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.error.AuthenticationFailedError
import tech.mohamedashraf.domain.usecase.Login
import tech.mohamedashraf.presentation.application.BaseViewModel
import tech.mohamedashraf.presentation.application.Route

class LoginViewModel(private val login: Login) : BaseViewModel<LoginViewState, LoginViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is LoginViewIntent.Login -> authenticate(it.email, it.password)
            }
        }
    }

    private fun authenticate(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(LoginViewState.Loading)
            val user = login(email, password)
            if (user != null) {
                state.postValue(LoginViewState.Authentification(if (user.verified) Route.BASE_HOME else Route.VERIFICATION))
            } else state.postValue(LoginViewState.Error(AuthenticationFailedError("")))
        } catch (error: Exception) {
            state.postValue(LoginViewState.Error(error))
        }
    }
}