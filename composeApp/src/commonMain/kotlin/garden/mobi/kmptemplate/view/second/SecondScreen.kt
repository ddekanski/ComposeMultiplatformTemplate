package garden.mobi.kmptemplate.view.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.ic_chevron_left
import garden.mobi.kmptemplate.view.second.SecondViewModel.SideEffect
import garden.mobi.kmptemplate.view.second.SecondViewModel.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SecondScreen(
    navController: NavHostController,
    viewModel: SecondViewModel = koinViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsState()

    Screen(
        state = state,
        viewModel = viewModel
    )

    LaunchedEffect(viewModel) {
        launch {
            viewModel.container.sideEffectFlow.collectLatest {
                handleSideEffect(sideEffect = it, navController = navController)
            }
        }
    }
}

@Composable
private fun Screen(
    state: State,
    viewModel: SecondViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Second screen",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.backClicked() }) {
                        Image(
                            painter = painterResource(Res.drawable.ic_chevron_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Text("Name received from route args: ${state.name}")
            }
        }
    )
}

private fun handleSideEffect(
    sideEffect: SideEffect,
    navController: NavHostController,
) = sideEffect.apply {
    when(this) {
        is SideEffect.NavigateBack -> navController.navigateUp()
    }
}