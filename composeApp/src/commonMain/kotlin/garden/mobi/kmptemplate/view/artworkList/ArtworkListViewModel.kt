package garden.mobi.kmptemplate.view.artworkList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.view.Route
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class ArtworkListViewModel(
    private val artworkRepository: ArtworkRepository,
) : ContainerHost<ArtworkListViewModel.State, ArtworkListViewModel.SideEffect>, ViewModel() {

    data class State(
        val artworks: List<ArtworkStub> = emptyList(),
    )

    sealed interface SideEffect {
        data class Navigate(val route: Route) : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private fun onCreate() = intent {
        val artworks = artworkRepository.getAllArtworkStubs()
        reduce {
            state.copy(
                artworks = artworks,
            )
        }
    }

    fun artworkClicked(artworkId: String) = intent {
        postSideEffect(SideEffect.Navigate(Route.ArtworkDetails(artworkId = artworkId)))
    }
}