package garden.mobi.kmptemplate.view

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import garden.mobi.kmptemplate.di.koinConfig
import garden.mobi.kmptemplate.view.greeting.GreetingScreen
import garden.mobi.kmptemplate.view.second.SecondScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = koinConfig()
    ){
        val navController = rememberNavController()

        MaterialTheme {
            NavHost(navController = navController, startDestination = Route.Greeting) {

                composable<Route.Greeting> { GreetingScreen(navController = navController) }

                composable<Route.Second> { SecondScreen(navController = navController) }

            }
        }
    }
}