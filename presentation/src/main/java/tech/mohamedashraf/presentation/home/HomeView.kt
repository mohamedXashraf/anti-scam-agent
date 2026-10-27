package tech.mohamedashraf.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import tech.mohamedashraf.presentation.application.Route
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme

@Composable
fun Home(navigate: (Route) -> Unit = {}) {

}

@Composable
private fun HomeView() {

}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    AntiScamAgentAppTheme {
        HomeView()
    }
}