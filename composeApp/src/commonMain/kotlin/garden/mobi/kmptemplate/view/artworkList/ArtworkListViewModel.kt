package garden.mobi.kmptemplate.view.artworkList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import garden.mobi.kmptemplate.Logger
import garden.mobi.kmptemplate.domain.model.ArtworkStub
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

class ArtworkListViewModel(
    private val artworkRepository: ArtworkRepository,
) : ContainerHost<ArtworkListViewModel.State, ArtworkListViewModel.SideEffect>, ViewModel() {

    data class State(
        val artworks: List<ArtworkStub> = emptyList(),
        val showProgressIndicator: Boolean = false,
        val errorDialog: AppError? = null,
    )

    sealed interface SideEffect {
        data class Navigate(val route: Route) : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private fun onCreate() = intent {
        artworkRepository
            .getAllArtworkStubs()
            .onStart { reduce { state.copy(showProgressIndicator = true) } }
            .onEach { artworkStubs ->
                reduce {
                    state.copy(
                        showProgressIndicator = false,
                        artworks = artworkStubs,
                    )
                }
            }
            .catch { handleError(it) }
            .launchIn(backgroundViewModelScope)
    }

    fun artworkClicked(artwork: ArtworkStub) = intent {
        postSideEffect(
            SideEffect.Navigate(
                Route.ArtworkDetails(
                    artworkId = artwork.id,
                    imagePlaceholderMemoryCacheKey = artwork.thumbnailUrl,
                    imageUrl = artwork.imageUrl,
                )
            )
        )
    }

    private fun handleError(throwable: Throwable) = intent {
        reduce { state.copy(showProgressIndicator = false) }
        Logger.e(throwable)
        reduce {
            state.copy(
                showProgressIndicator = false,
                errorDialog = AppError.UnexpectedError(throwable = throwable, positiveBtn = AppError.PositiveBtn.RETRY)
            )
        }
    }

    fun errorDialogDismissed() = intent {
        reduce { state.copy(errorDialog = null) }
    }
}