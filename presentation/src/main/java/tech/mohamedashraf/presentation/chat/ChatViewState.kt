package tech.mohamedashraf.presentation.chat

import tech.mohamedashraf.domain.rules.ChatMessage

sealed class ChatViewState {
    data object ViewInitialization : ChatViewState()
    data object Loading : ChatViewState()
    data class Data(val productId: String, val chat: List<ChatMessage>) : ChatViewState()
    data class Error(val error: Throwable) : ChatViewState()
}