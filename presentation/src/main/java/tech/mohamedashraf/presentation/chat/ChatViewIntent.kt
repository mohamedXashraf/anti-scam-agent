package tech.mohamedashraf.presentation.chat

sealed class ChatViewIntent {
    data class GetProductChatThread(val productId: String) : ChatViewIntent()
}