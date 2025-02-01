package garden.mobi.kmptemplate.view.artworkList

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.artwork_list_screen_title
import garden.mobi.kmptemplate.view.artworkList.ArtworkListViewModel.SideEffect
import garden.mobi.kmptemplate.view.artworkList.ArtworkListViewModel.State
import garden.mobi.kmptemplate.view.common.composable.ProgressIndicatorSurface
import garden.mobi.kmptemplate.view.errorDialog.ErrorDialog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArtworkListScreen(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ArtworkListViewModel = koinViewModel(),
) {
    val state by viewModel.container.stateFlow.collectAsState()

    Screen(
        state = state,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun Screen(
    state: State,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ArtworkListViewModel,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.artwork_list_screen_title),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
            )
        },
        content = { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(items = state.artworks, key = { it.id }) { artwork ->
                    with (sharedTransitionScope) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                            .clickable { viewModel.artworkClicked(artwork) }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                    .data(artwork.thumbnailUrl)
                                    .memoryCacheKey(artwork.thumbnailUrl)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedTransitionScope.rememberSharedContentState("${artwork.id}-image"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                    )
                                    .fillMaxSize()
                            )

                            Text(
                                text = artwork.title,
                                fontSize = 10.sp,
                                minLines = 1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedTransitionScope.rememberSharedContentState("${artwork.id}-title"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        zIndexInOverlay = 1f
                                    )
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp)
                                    .background(color = Color.LightGray.copy(alpha = .7f), shape = RoundedCornerShape(2.dp))
                                    .padding(horizontal = 4.dp)
                                    .zIndex(1f)
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
        }
    )

    if (state.showProgressIndicator) {
        ProgressIndicatorSurface()
    }

    state.errorDialog?.let { error ->
        ErrorDialog(error = error, onDismissed = { viewModel.errorDialogDismissed() })
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