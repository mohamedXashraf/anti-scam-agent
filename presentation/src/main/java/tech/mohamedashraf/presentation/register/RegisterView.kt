package tech.mohamedashraf.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
fun Register(navigate: (Route) -> Unit = {}, viewModel: RegisterViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = RegisterViewState.ViewInitialization)

    LoginView(
        onRegister = { name, email, password, passwordConfirmation -> viewModel.intent.postValue(RegisterViewIntent.Register(name, email, password, passwordConfirmation)) },
    )

    when(val viewState = state.value) {
        is RegisterViewState.ViewInitialization -> {}
        is RegisterViewState.Loading -> Loading()
        is RegisterViewState.Authentification -> navigate(viewState.destination)
        is RegisterViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun LoginView(onRegister: (String, String, String, String) -> Unit = { _, _, _, _ -> }) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirmation = remember { mutableStateOf("") }

    LazyColumn (
        modifier = Modifier.background(backgroundLight).fillMaxSize().padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(painter = painterResource(R.drawable.ic_logo), modifier = Modifier.height(150.dp), contentDescription = "")
            InputView(input = name, label = stringResource(R.string.name), keyboardType = KeyboardType.Text, onChange = { name.value = it })
            InputView(input = email, label = stringResource(R.string.email), keyboardType = KeyboardType.Email, onChange = { email.value = it })
            InputView(input = password, label = stringResource(R.string.password), keyboardType = KeyboardType.Password, onChange = { password.value = it })
            InputView(input = passwordConfirmation, label = stringResource(R.string.confirm_password), keyboardType = KeyboardType.Password, onChange = { passwordConfirmation.value = it })
            Spacer(modifier = Modifier.height(8.dp))
            ButtonView(label = stringResource(R.string.register)) {
                if (name.value.isBlank()) return@ButtonView
                if (email.value.isBlank()) return@ButtonView
                if (password.value.isBlank()) return@ButtonView
                if (passwordConfirmation.value.isBlank()) return@ButtonView
                onRegister(name.value, email.value, password.value, passwordConfirmation.value)
            }
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