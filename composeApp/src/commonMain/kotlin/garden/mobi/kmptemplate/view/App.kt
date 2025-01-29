package garden.mobi.kmptemplate.view

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinApplication
import garden.mobi.kmptemplate.di.koinConfig
import garden.mobi.kmptemplate.view.greeting.GreetingScreen

@Composable
@Preview
fun App() {
    KoinApplication(
        application = koinConfig()
    ){
        val navController = rememberNavController()
        MaterialTheme {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    GreetingScreen()
                }
            }
        }
    }
}