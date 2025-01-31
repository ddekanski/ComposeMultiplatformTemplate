package garden.mobi.kmptemplate.view.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.back
import composemultiplatformtemplate.composeapp.generated.resources.ic_chevron_left
import composemultiplatformtemplate.composeapp.generated.resources.name_received_from_route_args
import composemultiplatformtemplate.composeapp.generated.resources.second_screen_title
import garden.mobi.kmptemplate.view.second.SecondViewModel.SideEffect
import garden.mobi.kmptemplate.view.second.SecondViewModel.State
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
            viewModel.container.sideEffectFlow.collect {
                handleSideEffect(sideEffect = it, navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                        text = stringResource(Res.string.second_screen_title),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.backClicked() }) {
                        Image(
                            painter = painterResource(Res.drawable.ic_chevron_left),
                            contentDescription = stringResource(Res.string.back),
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
                Text(stringResource(Res.string.name_received_from_route_args, state.name))
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