package tech.mohamedashraf.presentation.chat

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.mohamedashraf.domain.usecase.GetProductDetails
import tech.mohamedashraf.presentation.application.BaseViewModel

class ChatViewModel(private val getProductDetails: GetProductDetails) : BaseViewModel<ChatViewState, ChatViewIntent>() {

    init {
        observeIntents {
            when (it) {
                is ChatViewIntent.GetProductChatThread -> getProductChatThread(it.productId)
            }
        }
    }

    private fun getProductChatThread(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            state.postValue(ChatViewState.Loading)
            val product = getProductDetails(productId)
            state.postValue(ChatViewState.Data(productId, product?.chat ?: listOf()))
        } catch (error: Exception) {
            state.postValue(ChatViewState.Error(error))
        }
    }
}