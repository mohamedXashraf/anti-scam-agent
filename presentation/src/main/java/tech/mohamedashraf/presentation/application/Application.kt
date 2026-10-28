package tech.mohamedashraf.presentation.application

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import tech.mohamedashraf.presentation.agent.Agent
import tech.mohamedashraf.presentation.chat.Chat
import tech.mohamedashraf.presentation.details.ProductDetails
import tech.mohamedashraf.presentation.home.Home
import tech.mohamedashraf.presentation.login.Login
import tech.mohamedashraf.presentation.register.Register
import tech.mohamedashraf.presentation.splash.Splash
import tech.mohamedashraf.presentation.ui.theme.AntiScamAgentAppTheme
import tech.mohamedashraf.presentation.verification.Verification

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Application() {

    val navController = rememberNavController()

    val navigator: (Route) -> Unit = { navigate(navController = navController, route = it) }

    AntiScamAgentAppTheme {
        Scaffold (modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = Route.SPLASH, builder = {
                composable<Route.SPLASH> { Splash(navigate = navigator) }
                composable<Route.LOGIN> { Login(navigate = navigator) }
                composable<Route.REGISTER> { Register(navigate = navigator) }
                composable<Route.VERIFICATION> { Verification(navigate = navigator) }
                composable<Route.HOME> { Home(navigate = navigator) }
                composable<Route.ITEM_DETAILS> { ProductDetails(navigate = navigator, productId = it.toRoute<Route.ITEM_DETAILS>().productId) }
                composable<Route.CHAT> { Chat(navigate = navigator, productId = it.toRoute<Route.CHAT>().productId) }
                composable<Route.AGENT> { Agent(navigate = navigator, productId = it.toRoute<Route.AGENT>().productId) }
            })
        }
    }
}

private fun navigate(navController: NavHostController, route: Route) {
    when (route) {
        Route.BACK -> if (!navController.popBackStack()) (navController.context as Activity).finish()
        Route.BASE_SPLASH -> navController.navigate(Route.SPLASH) { popUpTo(Route.HOME) { inclusive = true } }
        Route.BASE_HOME -> navController.navigate(Route.HOME) { popUpTo(navController.graph.id) }
        Route.RESET_HOME -> navController.navigate(Route.HOME) { popUpTo(Route.HOME) { inclusive = true } }
        Route.BASE_LOGIN -> navController.navigate(Route.LOGIN) { popUpTo(navController.graph.id) }
        Route.BASE_VERIFICATION -> navController.navigate(Route.VERIFICATION) { popUpTo(navController.graph.id) }
        else -> navController.navigate(route)
    }
}