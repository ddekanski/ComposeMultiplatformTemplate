package garden.mobi.kmptemplate.view.artworkDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.view.Route
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class ArtworkDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val artworkRepository: ArtworkRepository,
) : ContainerHost<ArtworkDetailsViewModel.State, ArtworkDetailsViewModel.SideEffect>, ViewModel() {

    data class State(
        val title: String = "",
        val imageUrl: String? = null,
        val date: String = "",
        val description: String? = null,
        val artist: String = "",
        val type: String = "",
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private val args: Route.ArtworkDetails by lazy { savedStateHandle.toRoute() }

    private fun onCreate() = intent {
        val artwork = artworkRepository.getArtwork(artworkId = args.artworkId)
        reduce {
            state.copy(
                title = artwork.title,
                imageUrl = artwork.imageUrl,
                date = artwork.date,
                description = artwork.description,
                artist = artwork.artist,
                type = artwork.type,
            )
        }
    }

    fun backClicked() = intent {
        postSideEffect(SideEffect.NavigateBack)
    }
}