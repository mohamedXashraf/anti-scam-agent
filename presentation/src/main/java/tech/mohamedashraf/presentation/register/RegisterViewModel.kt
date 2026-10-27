package tech.mohamedashraf.presentation.register

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.error.AuthenticationFailedError
import tech.mohamedashraf.domain.usecase.Register
import tech.mohamedashraf.presentation.application.BaseViewModel
import tech.mohamedashraf.presentation.application.Route

class RegisterViewModel(private val register: Register) : BaseViewModel<RegisterViewState, RegisterViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is RegisterViewIntent.Register -> authenticate(it.name, it.email, it.password, it.passwordConfirmation)
            }
        }
    }

    private fun authenticate(name: String, email: String, password: String, passwordConfirmation: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(RegisterViewState.Loading)
            val user = register(name, email, password, passwordConfirmation)
            if (user != null) {
                state.postValue(RegisterViewState.Authentification(if (user.verified) Route.BASE_HOME else Route.VERIFICATION))
            } else state.postValue(RegisterViewState.Error(AuthenticationFailedError("")))
        } catch (error: Exception) {
            state.postValue(RegisterViewState.Error(error))
        }
    }
}