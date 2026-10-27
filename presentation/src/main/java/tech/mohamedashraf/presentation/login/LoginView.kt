package tech.mohamedashraf.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.ButtonView
import tech.mohamedashraf.presentation.ui.component.InputView
import tech.mohamedashraf.presentation.ui.component.Loading
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.utils.errorMessage

@Composable
fun Login(navigate: (Route) -> Unit = {}, viewModel: LoginViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = LoginViewState.ViewInitialization)

    LoginView(
        onLogin = { email, password -> viewModel.intent.postValue(LoginViewIntent.Login(email, password)) },
        onRegister = {
            viewModel.state.postValue(LoginViewState.ViewInitialization)
            navigate(Route.REGISTER)
        }
    )

    when(val viewState = state.value) {
        is LoginViewState.ViewInitialization -> {}
        is LoginViewState.Loading -> Loading()
        is LoginViewState.Authentification -> navigate(viewState.destination)
        is LoginViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun LoginView(onLogin: (String, String) -> Unit = { _, _ -> }, onRegister: () -> Unit = {}) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LazyColumn (
        modifier = Modifier.background(backgroundLight).fillMaxSize().padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(painter = painterResource(R.drawable.ic_logo), modifier = Modifier.height(150.dp), contentDescription = "")
            InputView(input = email, label = stringResource(R.string.email), keyboardType = KeyboardType.Email, onChange = { email.value = it })
            InputView(input = password, label = stringResource(R.string.password), keyboardType = KeyboardType.Password, onChange = { password.value = it })
            Spacer(modifier = Modifier.height(8.dp))
            ButtonView(label = stringResource(R.string.login)) {
                if (email.value.isBlank()) return@ButtonView
                if (password.value.isBlank()) return@ButtonView
                onLogin(email.value, password.value)
            }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonView(label = stringResource(R.string.register), action = onRegister)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginViewPreview() {
    AntiScamAgentAppTheme {
        LoginView()
    }
}