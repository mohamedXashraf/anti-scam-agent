package tech.mohamedashraf.presentation.agent

import tech.mohamedashraf.domain.rules.DealVerification

sealed class AgentViewState {
    data object ViewInitialization : AgentViewState()
    data object Loading : AgentViewState()
    data class Data(val dealVerification: DealVerification) : AgentViewState()
    data class Error(val error: Throwable) : AgentViewState()
}