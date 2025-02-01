package garden.mobi.kmptemplate.view.artworkDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import garden.mobi.kmptemplate.Logger
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.view.Route
import garden.mobi.kmptemplate.view.common.viewModel.backgroundViewModelScope
import garden.mobi.kmptemplate.view.errorDialog.AppError
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
            .onEach { artwork ->
                Logger.d("Loaded artwork '${artwork.title}'")
                reduce {
                    state.copy(
                        title = artwork.title,
                        imageUrl = artwork.imageUrl,
                        date = artwork.date,
                        description = artwork.description,
                        artist = artwork.artist,
                        type = artwork.type,
                        showProgressIndicator = false,
                    )
                }
            }
            .catch { handleError(it) }
            .launchIn(backgroundViewModelScope)
    }

    fun backClicked() = intent {
        postSideEffect(SideEffect.NavigateBack)
    }

    private fun handleError(throwable: Throwable) = intent {
        reduce { state.copy(showProgressIndicator = false) }
        Logger.e(throwable)
        reduce {
            state.copy(
                showProgressIndicator = false,
                errorDialog = AppError.UnexpectedError(throwable = throwable)
            )
        }
    }

    fun errorDialogDismissed(error: AppError) = intent {
        reduce { state.copy(errorDialog = null) }

        if (error.finishOnDismiss) postSideEffect(SideEffect.NavigateBack)
    }
}