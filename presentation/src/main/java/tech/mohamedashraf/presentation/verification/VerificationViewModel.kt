package tech.mohamedashraf.presentation.verification

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.CheckUserVerification
import tech.mohamedashraf.domain.usecase.Logout
import tech.mohamedashraf.domain.usecase.SendVerificationEmail
import tech.mohamedashraf.presentation.application.BaseViewModel

class VerificationViewModel(
    private val checkUserVerification: CheckUserVerification,
    private val sendVerificationEmail: SendVerificationEmail,
    private val logout: Logout
) : BaseViewModel<VerificationViewState, VerificationViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is VerificationViewIntent.CheckUserVerification -> checkUserVerificationState()
                is VerificationViewIntent.SendVerificationEmail -> resendVerificationEmail()
                is VerificationViewIntent.Logout -> exit()
            }
        }
    }

    private fun checkUserVerificationState() = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(VerificationViewState.Loading)
            val isVerified = checkUserVerification()
            state.postValue(if (isVerified) VerificationViewState.Verified else VerificationViewState.UnVerified)
        } catch (error: Exception) {
            state.postValue(VerificationViewState.Error(error))
        }
    }

    private fun resendVerificationEmail() = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(VerificationViewState.Loading)
            sendVerificationEmail()
            state.postValue(VerificationViewState.VerificationEmailSent)
        } catch (error: Exception) {
            state.postValue(VerificationViewState.Error(error))
        }
    }

    private fun exit() = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(VerificationViewState.Loading)
            logout()
            state.postValue(VerificationViewState.LoggedOut)
        } catch (error: Exception) {
            state.postValue(VerificationViewState.Error(error))
        }
    }
}