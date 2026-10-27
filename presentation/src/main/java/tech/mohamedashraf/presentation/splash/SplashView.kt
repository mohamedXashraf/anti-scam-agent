package tech.mohamedashraf.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.component.TextView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.utils.errorMessage

@Composable
fun Splash(navigate: (Route) -> Unit = {}, viewModel: SplashViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = SplashViewState.Loading)

    SplashView()

    when(val viewState = state.value) {
        is SplashViewState.Loading -> LaunchedEffect(Unit){ viewModel.intent.postValue(SplashViewIntent.CheckUser) }
        is SplashViewState.Authentification -> {} //navigate(Route.BASE_HOME)
        is SplashViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun SplashView() {
    Column(
        modifier = Modifier.background(backgroundLight).fillMaxSize().padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp).weight(1f))
        Image(painter = painterResource(R.drawable.ic_logo), modifier = Modifier.height(200.dp), contentDescription = "")
        TextView(text = stringResource(R.string.app_name), size = 24, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(16.dp).weight(1f))
        Image(painter = painterResource(R.drawable.ic_hack_logo), modifier = Modifier.height(70.dp), contentDescription = "")
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashViewPreview() {
    AntiScamAgentAppTheme {
        SplashView()
    }
}
