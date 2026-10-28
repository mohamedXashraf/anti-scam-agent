package tech.mohamedashraf.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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

@Composable
fun Home(navigate: (Route) -> Unit = {}, viewModel: HomeViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = HomeViewState.ViewInitialization)
    val products = remember { mutableStateOf<List<Product>>(listOf()) }

    HomeView(
        products = products.value,
        onSelect = { navigate(Route.ITEM_DETAILS(it.id)) },
        onLogout = { viewModel.intent.postValue(HomeViewIntent.Logout) }
    )

    when(val viewState = state.value) {
        is HomeViewState.ViewInitialization -> LaunchedEffect(1) { viewModel.intent.postValue(HomeViewIntent.GetMarketplaceProducts) }
        is HomeViewState.Loading -> Loading()
        is HomeViewState.Data -> LaunchedEffect(1) { products.value = viewState.products }
        is HomeViewState.LoggedOut -> { navigate(Route.BASE_SPLASH) }
        is HomeViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun HomeView(products: List<Product> = listOf(), onSelect: (Product) -> Unit = {}, onLogout: () -> Unit = {}) {
    LazyColumn (modifier = Modifier.background(backgroundLight).fillMaxSize().padding(16.dp)) {
        item { Spacer(modifier = Modifier.height(32.dp)) }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(R.drawable.ic_logo), modifier = Modifier.height(56.dp), contentDescription = "")
                TextView(text = stringResource(R.string.app_name), size = 24, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(24.dp).weight(1f))
                IconButtonView(icon = Icons.AutoMirrored.Rounded.Logout, tint = primaryLight, action = onLogout)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(products) { ProductItem(it) { onSelect(it) } }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun ProductItem(product: Product, onSelect: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().height(100.dp).padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onSelect
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier.width(100.dp),
                model = product.imageUrl,
                contentDescription = product.name,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(2f),
            ) {
                TextView(text = product.name, size = 16, fontWeight = FontWeight.Bold)
                TextView(text = product.description, size = 12, maxLines = 1, color = outlineLight)
            }
            Spacer(modifier = Modifier.width(16.dp))
            TextView(text = "$ ${product.price.format()}", size = 14, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    AntiScamAgentAppTheme {
        HomeView(
            products = listOf(
                Product(
                    id = "123asd",
                    name = "Product 1",
                    description = "product description",
                    price = 12.50,
                    imageUrl = "https://images.pexels.com/photos/38921150/pexels-photo-38921150.jpeg",
                    sellerId = "12341asdqw",
                    chat = listOf(
                        ChatMessage(text = "it this item still available?", sender = ChatParticipant.ME, sentAt = 1793195203971),
                        ChatMessage(text = "yes it is available, just send me a deposit", sender = ChatParticipant.SELLER, sentAt = 1793195285667),
                        ChatMessage(text = "ok will get back to you", sender = ChatParticipant.ME, sentAt = 1793195394135)
                    )
                )
            )
        )
    }
}