package tech.mohamedashraf.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.domain.rules.ChatMessage
import tech.mohamedashraf.domain.rules.ChatParticipant
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.Loading
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.component.TextView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.outlineLight
import tech.mohamedashraf.presentation.ui.theme.inverseOnSurfaceLight
import tech.mohamedashraf.presentation.utils.date
import tech.mohamedashraf.presentation.utils.errorMessage

@Composable
fun Chat(navigate: (Route) -> Unit = {}, productId: String, viewModel: ChatViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = ChatViewState.ViewInitialization)
    val chat = remember { mutableStateOf<List<ChatMessage>>(listOf()) }

    ChatView(
        chat = chat.value,
    )

    when(val viewState = state.value) {
        is ChatViewState.ViewInitialization -> LaunchedEffect(1) { viewModel.intent.postValue(ChatViewIntent.GetProductChatThread(productId)) }
        is ChatViewState.Loading -> Loading()
        is ChatViewState.Data -> LaunchedEffect(1) { chat.value = viewState.chat }
        is ChatViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun ChatView(chat: List<ChatMessage> = listOf()) {
    LazyColumn (modifier = Modifier
        .background(backgroundLight)
        .fillMaxSize()
        .padding(16.dp)) {
        item { Spacer(modifier = Modifier.height(32.dp)) }
        items(chat) { MessageItem(it) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun MessageItem(message: ChatMessage) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = if (message.sender == ChatParticipant.ME) backgroundLight else inverseOnSurfaceLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TextView(modifier = Modifier.padding(horizontal = 16.dp), text = "${message.sender.name}:   ${message.text}", size = 14, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        TextView(modifier = Modifier.padding(horizontal = 16.dp), text = message.sentAt.date(), size = 11, color = outlineLight)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    AntiScamAgentAppTheme {
        ChatView(listOf(
            ChatMessage(text = "it this item still available?", sender = ChatParticipant.ME, sentAt = 1793195203971),
            ChatMessage(text = "yes it is available", sender = ChatParticipant.SELLER, sentAt = 1793195285667),
            ChatMessage(text = "is it new or used?", sender = ChatParticipant.ME, sentAt = 1793195394135),
            ChatMessage(text = "it is used like new", sender = ChatParticipant.SELLER, sentAt = 1793195424135),
            ChatMessage(text = "can we meet and see it first", sender = ChatParticipant.ME, sentAt = 1793195454135),
            ChatMessage(text = "ok sure", sender = ChatParticipant.SELLER, sentAt = 1793195484135),
        ))
    }
}