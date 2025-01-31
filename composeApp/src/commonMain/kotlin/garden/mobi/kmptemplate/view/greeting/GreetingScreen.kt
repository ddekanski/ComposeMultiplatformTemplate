package garden.mobi.kmptemplate.view.greeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.compose_multiplatform
import composemultiplatformtemplate.composeapp.generated.resources.go_to_second_screen
import composemultiplatformtemplate.composeapp.generated.resources.tap_me
import garden.mobi.kmptemplate.view.greeting.GreetingViewModel.SideEffect
import garden.mobi.kmptemplate.view.greeting.GreetingViewModel.State
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GreetingScreen(
    navController: NavHostController,
    viewModel: GreetingViewModel = koinViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsState()

    Screen(
        state = state,
        viewModel = viewModel
    )

    LaunchedEffect(viewModel) {
        launch {
            viewModel.container.sideEffectFlow.collect {
                handleSideEffect(sideEffect = it, navController = navController)
            }
        }
    }
}

@Composable
private fun Screen(
    state: State,
    viewModel: GreetingViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(onClick = { viewModel.tapMeBtnClicked() }) {
            Text(stringResource(Res.string.tap_me))
        }

        AnimatedVisibility(state.showContent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                )

                Text(
                    text = state.greeting,
                    textAlign = TextAlign.Center,
                )

                Button(onClick = { viewModel.goToSecondScreenBtnClicked() }) {
                    Text(stringResource(Res.string.go_to_second_screen))
                }
            }
        }
    }
}

private fun handleSideEffect(
    sideEffect: SideEffect,
    navController: NavHostController,
) = sideEffect.apply {
    when(this) {
        is SideEffect.Navigate -> navController.navigate(route)
    }
}