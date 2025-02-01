package garden.mobi.kmptemplate.view.artworkDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.back
import composemultiplatformtemplate.composeapp.generated.resources.ic_back_white_on_semitransparent
import garden.mobi.kmptemplate.view.artworkDetails.ArtworkDetailsViewModel.SideEffect
import garden.mobi.kmptemplate.view.artworkDetails.ArtworkDetailsViewModel.State
import garden.mobi.kmptemplate.view.common.composable.ProgressIndicatorSurface
import garden.mobi.kmptemplate.view.errorDialog.ErrorDialog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ArtworkDetailsScreen(
    navController: NavHostController,
    viewModel: ArtworkDetailsViewModel = koinViewModel(),
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
    viewModel: ArtworkDetailsViewModel,
) {
    Box()
    {
        IconButton(
            onClick = { viewModel.backClicked() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp)
                .statusBarsPadding()
                .zIndex(2f)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_back_white_on_semitransparent),
                contentDescription = stringResource(Res.string.back),
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            state.imageUrl?.let {
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)

                )
            }

            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = state.type,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = state.artist,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .padding(horizontal = 16.dp)
            )

            state.description?.let {
                val richTextState = rememberRichTextState()
                richTextState.setHtml(state.description)
                RichText(
                    state = richTextState,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 8.dp)
            )
        }
    }

    if (state.showProgressIndicator) {
        ProgressIndicatorSurface()
    }

    state.errorDialog?.let { error ->
        ErrorDialog(error = error, onDismissed = { viewModel.errorDialogDismissed(error = error) })
    }
}

private fun handleSideEffect(
    sideEffect: SideEffect,
    navController: NavHostController,
) = sideEffect.apply {
    when(this) {
        is SideEffect.NavigateBack -> navController.navigateUp()
    }
}