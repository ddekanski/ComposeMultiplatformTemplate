package garden.mobi.kmptemplate.view.artworkList

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
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class ArtworkListViewModel(
    private val artworkStubRepository: ArtworkStubRepository,
) : ContainerHost<ArtworkListViewModel.State, ArtworkListViewModel.SideEffect>, ViewModel() {

    data class State(
        val artworks: List<ArtworkStub> = emptyList(),
        val isRefreshing: Boolean = false,
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
        artworkStubRepository
            .getAllArtworkStubs()
            .onEach { dataResponse ->
                when (dataResponse) {
                    is DataResponse.Data -> {
                        val artworkStubs = dataResponse.data
                        reduce {
                            state.copy(
                                artworks = artworkStubs,
                                isRefreshing = false,
                            )
                        }
                    }

                    is DataResponse.Loading -> {
                        reduce {
                            state.copy(isRefreshing = true)
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
        when (artwork.isFavorite) {
            true -> artworkStubRepository.removeFromFavorites(artwork.id)
            false -> artworkStubRepository.addToFavorites(artwork.id)
        }
    }

    fun favoritesIconClicked() = intent {
        postSideEffect(SideEffect.Navigate(Route.FavoriteArtworkList))
    }

    fun refreshActionInvoked() = intent {
        try {
            reduce { state.copy(isRefreshing = true) }
            artworkStubRepository.refresh()
        } catch (throwable: Throwable) {
            handleError(throwable = throwable)
        }
    }

    private fun handleError(throwable: Throwable) = intent {
        Logger.e(throwable)
        reduce {
            state.copy(
                isRefreshing = false,
                errorDialog = AppError.UnexpectedError(throwable = throwable, positiveBtn = AppError.PositiveBtn.RETRY)
            )
        }
    }

    fun errorDialogDismissed() = intent {
        try {
            reduce { state.copy(errorDialog = null, isRefreshing = true) }
            artworkStubRepository.refresh()
        } catch (throwable: Throwable) {
            handleError(throwable = throwable)
        }
    }
}