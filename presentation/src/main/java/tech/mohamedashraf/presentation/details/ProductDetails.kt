package tech.mohamedashraf.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.ViewInAr
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.domain.rules.ChatMessage
import tech.mohamedashraf.domain.rules.ChatParticipant
import tech.mohamedashraf.domain.rules.Product
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.IconButtonView
import tech.mohamedashraf.presentation.ui.component.Loading
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.component.TextView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.ui.theme.outlineLight
import tech.mohamedashraf.presentation.ui.theme.primaryLight
import tech.mohamedashraf.presentation.utils.errorMessage
import tech.mohamedashraf.presentation.utils.format
import kotlin.uuid.Uuid

@Composable
fun ProductDetails(navigate: (Route) -> Unit = {}, productId: String, viewModel: ProductDetailsViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = ProductDetailsViewState.ViewInitialization)
    val product = remember { mutableStateOf<Product?>(null) }

    ProductDetailsView(
        product = product.value,
        openChat = { navigate(Route.CHAT(productId)) },
        startAgent = { navigate(Route.AGENT(productId)) }
    )

    when(val viewState = state.value) {
        is ProductDetailsViewState.ViewInitialization -> LaunchedEffect(1) { viewModel.intent.postValue(ProductDetailsViewIntent.GetProductDetails(productId)) }
        is ProductDetailsViewState.Loading -> Loading()
        is ProductDetailsViewState.Data -> LaunchedEffect(1) { product.value = viewState.product }
        is ProductDetailsViewState.InvalidProduct -> MessageView(message = stringResource(R.string.general_error), onDismiss = { navigate.invoke(Route.BACK) })
        is ProductDetailsViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ProductDetailsView(product: Product? = null, openChat: () -> Unit = {}, startAgent: () -> Unit = {}) {
    Scaffold(modifier = Modifier
        .background(backgroundLight)
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn (modifier = Modifier.fillMaxSize()) {
            item { Spacer(modifier = Modifier.height(40.dp)) }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().height(320.dp),
                    colors = CardDefaults.cardColors(containerColor = outlineLight),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = product?.imageUrl ?: "",
                        contentDescription = product?.name ?: "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                TextView(modifier = Modifier.padding(horizontal = 12.dp), text = product?.name ?: "", size = 32, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                TextView(modifier = Modifier.padding(horizontal = 12.dp), text = product?.description ?: "", size = 16, color = outlineLight)
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextView(text = "$ ${product?.price?.format() ?: 0.0.format()}", size = 20, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(16.dp).weight(1f))
                    IconButtonView(icon = Icons.Rounded.ChatBubbleOutline, tint = primaryLight, openChat)
                    IconButtonView(icon = Icons.Rounded.ViewInAr, tint = primaryLight, startAgent)
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    AntiScamAgentAppTheme {
        ProductDetailsView(Product(
            id = Uuid.random().toString(),
            name = "MX Master 4",
            description = "Meet the MX Master 4 that brings immersive control and precision you can feel with customizable haptic feedback on specific actions. Save up to 33% of your time with Actions Ring shortcuts and MX Master 4, by accessing tools and filters at your cursor. Additional features: 2x better connectivity, ultra fast scrolling with the MagSpeed scroll wheel, 8k DPI any surface tracking, including glass & Logi Options+ for customization.",
            price = 119.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/antiscam-agent.firebasestorage.app/o/Logitech4.webp?alt=media&token=b7726d28-92db-4573-aee0-28e5dd2019a2",
            sellerId = Uuid.random().toString(),
            chat = listOf(
                ChatMessage(text = "it this item still available?", sender = ChatParticipant.ME, sentAt = 1793195203971),
                ChatMessage(text = "yes it is available", sender = ChatParticipant.SELLER, sentAt = 1793195285667),
                ChatMessage(text = "is it new or used?", sender = ChatParticipant.ME, sentAt = 1793195394135),
                ChatMessage(text = "it is used like new", sender = ChatParticipant.SELLER, sentAt = 1793195424135),
                ChatMessage(text = "can we meet and see it first", sender = ChatParticipant.ME, sentAt = 1793195454135),
                ChatMessage(text = "ok sure", sender = ChatParticipant.SELLER, sentAt = 1793195484135),
            )
        ))
    }
}