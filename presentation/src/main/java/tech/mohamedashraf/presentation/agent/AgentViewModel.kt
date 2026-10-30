package tech.mohamedashraf.presentation.agent

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.StartAgenticDealVerification
import tech.mohamedashraf.presentation.application.BaseViewModel
import java.io.File

class AgentViewModel(private val startAgenticDealVerification: StartAgenticDealVerification) : BaseViewModel<AgentViewState, AgentViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is AgentViewIntent.StartAgenticCheck -> startAgenticDealCheck(it.productId, it.liveProductVideo)
            }
        }
    }

    private fun startAgenticDealCheck(productId: String, video: File?) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(AgentViewState.Loading)
            state.postValue(AgentViewState.Data(startAgenticDealVerification(productId, video)))
        } catch (error: Exception) {
            state.postValue(AgentViewState.Error(error))
        }
    }
}