package tech.mohamedashraf.presentation.verification

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import tech.mohamedashraf.domain.usecase.Logout
import tech.mohamedashraf.presentation.R
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.component.ButtonView
import tech.mohamedashraf.presentation.ui.component.DialogType
import tech.mohamedashraf.presentation.ui.component.InputView
import tech.mohamedashraf.presentation.ui.component.Loading
import tech.mohamedashraf.presentation.ui.component.MessageView
import tech.mohamedashraf.presentation.ui.component.TextView
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.ui.theme.backgroundLight
import tech.mohamedashraf.presentation.utils.errorMessage

@Composable
fun Verification(navigate: (Route) -> Unit = {}, viewModel: VerificationViewModel = koinViewModel()) {

    val state = viewModel.state.observeAsState(initial = VerificationViewState.ViewInitialization)

    VerificationView(
        checkVerification = { viewModel.intent.postValue(VerificationViewIntent.CheckUserVerification) },
        resendVerificationEmail = { viewModel.intent.postValue(VerificationViewIntent.SendVerificationEmail) },
        logout = { viewModel.intent.postValue(VerificationViewIntent.Logout) }
    )

    when(val viewState = state.value) {
        is VerificationViewState.ViewInitialization -> {}
        is VerificationViewState.Loading -> Loading()
        is VerificationViewState.Verified -> { navigate(Route.BASE_HOME) }
        is VerificationViewState.VerificationEmailSent -> MessageView(message = stringResource(R.string.resend_verification_email), type = DialogType.SUCCESS)
        is VerificationViewState.LoggedOut -> { navigate(Route.BASE_SPLASH) }
        is VerificationViewState.UnVerified -> MessageView(message = stringResource(R.string.unverified_account))
        is VerificationViewState.Error -> MessageView(message = viewState.error.errorMessage())
    }
}

@Composable
private fun VerificationView(checkVerification: () -> Unit = {}, resendVerificationEmail: () -> Unit = {}, logout: () -> Unit = {}) {
    LazyColumn (
        modifier = Modifier.background(backgroundLight).fillMaxSize().padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(painter = painterResource(R.drawable.ic_logo), modifier = Modifier.height(150.dp), contentDescription = "")
            Spacer(modifier = Modifier.height(32.dp))
            TextView(text = stringResource(R.string.unverified_account), align = TextAlign.Center, size = 16)
            Spacer(modifier = Modifier.height(32.dp))
            ButtonView(label = stringResource(R.string.recheck_verification), action = checkVerification)
            Spacer(modifier = Modifier.height(8.dp))
            ButtonView(label = stringResource(R.string.resend_verification_email), action = resendVerificationEmail)
            Spacer(modifier = Modifier.height(8.dp))
            ButtonView(label = stringResource(R.string.logout), action = logout)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerificationViewPreview() {
    AntiScamAgentAppTheme {
        VerificationView()
    }
}