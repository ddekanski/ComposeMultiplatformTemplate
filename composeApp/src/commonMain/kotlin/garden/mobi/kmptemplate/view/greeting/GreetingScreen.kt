package garden.mobi.kmptemplate.view.greeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.compose_multiplatform
import garden.mobi.kmptemplate.view.Route
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GreetingScreen(
    args: Route.Greeting,
    greetingViewModel: GreetingViewModel = koinViewModel(),
) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Name received from route args: ${args.name}")

            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                val greeting = greetingViewModel.sayHello("Koin")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null,
                    )

                    Text(
                        text = "Compose: $greeting",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}