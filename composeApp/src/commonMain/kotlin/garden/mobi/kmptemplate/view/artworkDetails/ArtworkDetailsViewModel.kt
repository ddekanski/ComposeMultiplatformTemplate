package garden.mobi.kmptemplate.view.artworkDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.cannot_load_artwork
import garden.mobi.kmptemplate.Logger
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.util.DataResponse
import garden.mobi.kmptemplate.view.Route
import garden.mobi.kmptemplate.view.common.viewModel.backgroundViewModelScope
import garden.mobi.kmptemplate.view.errorDialog.AppError
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.jetbrains.compose.resources.getString
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class ArtworkDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val artworkRepository: ArtworkRepository,
) : ContainerHost<ArtworkDetailsViewModel.State, ArtworkDetailsViewModel.SideEffect>, ViewModel() {

    data class State(
        val artworkId: String,
        val title: String = "",
        val imageUrl: String? = null,
        val imagePlaceholderMemoryCacheKey: String? = null,
        val date: String = "",
        val description: String? = null,
        val artist: String = "",
        val type: String = "",
        val isFavorite: Boolean = false,
        val showProgressIndicator: Boolean = false,
        val errorDialog: AppError? = null,
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
    }

    private val args: Route.ArtworkDetails by lazy { savedStateHandle.toRoute() }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(
            artworkId = args.artworkId,
            imageUrl = args.imageUrl,
            imagePlaceholderMemoryCacheKey = args.imagePlaceholderMemoryCacheKey,
        ),
        onCreate = { onCreate() }
    )

    private fun onCreate() = intent {
        artworkRepository
            .getArtwork(artworkId = args.artworkId)
            .onStart { reduce { state.copy(showProgressIndicator = true) } }
            .onEach { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Data -> {
                        val artwork = dataResponse.data
                        Logger.d("Loaded artwork '${artwork.title}'")
                        reduce {
                            state.copy(
                                title = artwork.title,
                                imageUrl = artwork.imageUrl,
                                date = artwork.date,
                                description = artwork.description,
                                artist = artwork.artist,
                                type = artwork.type,
                                isFavorite = artwork.isFavorite,
                                showProgressIndicator = false,
                            )
                        }
                    }

                    is DataResponse.Loading -> {
                        reduce {
                            state.copy(showProgressIndicator = true)
                        }
                    }

                    is DataResponse.Error -> {
                        handleError(dataResponse.throwable)
                    }
                }
            }
            .launchIn(backgroundViewModelScope)
    }

    fun backClicked() = intent {
        postSideEffect(SideEffect.NavigateBack)
    }

    private fun handleError(throwable: Throwable) = intent {
        reduce { state.copy(showProgressIndicator = false) }
        Logger.e(throwable)
        val message = getString(Res.string.cannot_load_artwork)
        reduce {
            state.copy(
                showProgressIndicator = false,
                errorDialog = AppError.OtherError(message = message, finishOnDismiss = true)
            )
        }
    }

    fun errorDialogDismissed(error: AppError) = intent {
        reduce { state.copy(errorDialog = null) }
        if (error.finishOnDismiss) postSideEffect(SideEffect.NavigateBack)
    }

    fun favIconClicked() = intent {
        when (state.isFavorite) {
            true -> artworkRepository.removeFromFavorites(state.artworkId)
            false -> artworkRepository.addToFavorites(state.artworkId)
        }
    }
}