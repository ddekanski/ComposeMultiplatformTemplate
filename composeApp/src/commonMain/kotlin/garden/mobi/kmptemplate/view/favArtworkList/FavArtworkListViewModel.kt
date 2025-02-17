package garden.mobi.kmptemplate.view.favArtworkList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import garden.mobi.kmptemplate.Logger
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.repository.ArtworkStubRepository
import garden.mobi.kmptemplate.domain.util.DataResponse
import garden.mobi.kmptemplate.view.Route
import garden.mobi.kmptemplate.view.common.viewModel.backgroundViewModelScope
import garden.mobi.kmptemplate.view.errorDialog.AppError
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class FavArtworkListViewModel(
    private val artworkStubRepository: ArtworkStubRepository,
) : ContainerHost<FavArtworkListViewModel.State, FavArtworkListViewModel.SideEffect>, ViewModel() {

    data class State(
        val artworks: List<ArtworkStub> = emptyList(),
        val showProgressIndicator: Boolean = false,
        val errorDialog: AppError? = null,
    )

    sealed interface SideEffect {
        data class Navigate(val route: Route) : SideEffect
        data object NavigateUp : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private fun onCreate() = intent {
        artworkStubRepository
            .getFavoriteArtworkStubs()
            .onStart { reduce { state.copy(showProgressIndicator = true) } }
            .onEach { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Data -> {
                        val artworkStubs = dataResponse.data
                        reduce {
                            state.copy(
                                artworks = artworkStubs,
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

    fun artworkFavIconClicked(artwork: ArtworkStub) = intent {
        artworkStubRepository.removeFromFavorites(artwork.id)
    }

    fun backClicked() = intent {
        postSideEffect(SideEffect.NavigateUp)
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
        try {
            reduce { state.copy(errorDialog = null, showProgressIndicator = true) }
            artworkStubRepository.refresh()
        } catch (throwable: Throwable) {
            handleError(throwable = throwable)
        }
    }
}