package garden.mobi.kmptemplate.view.second

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import garden.mobi.kmptemplate.view.Route
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class SecondViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ContainerHost<SecondViewModel.State, SecondViewModel.SideEffect>, ViewModel() {

    data class State(
        val name: String = "",
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private val args: Route.Second by lazy { savedStateHandle.toRoute() }

    private fun onCreate() = intent {
        reduce {
            state.copy(
                name = args.name,
            )
        }
    }

    fun backClicked() = intent {
        postSideEffect(SideEffect.NavigateBack)
    }
}