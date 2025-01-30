package garden.mobi.kmptemplate.view.greeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import composemultiplatformtemplate.composeapp.generated.resources.Res
import composemultiplatformtemplate.composeapp.generated.resources.hello_user_from_platform_name
import composemultiplatformtemplate.composeapp.generated.resources.user_not_found
import garden.mobi.kmptemplate.domain.repository.UserRepository
import garden.mobi.kmptemplate.getPlatform
import garden.mobi.kmptemplate.view.Route
import org.jetbrains.compose.resources.getString
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

class GreetingViewModel(
    private val repository: UserRepository,
) : ContainerHost<GreetingViewModel.State, GreetingViewModel.SideEffect>, ViewModel() {

    data class State(
        val name: String = "",
        val showContent: Boolean = false,
        val greeting: String = "",
    )

    sealed interface SideEffect {
        data class Navigate(val route: Route) : SideEffect
    }

    override val container = viewModelScope.container<State, SideEffect>(
        initialState = State(),
        onCreate = { onCreate() }
    )

    private fun onCreate() = intent {
        reduce {
            state.copy(
                name = "Koin",
            )
        }
    }

    fun tapMeBtnClicked() = intent {
        if (!state.showContent) {
            val foundUser = repository.findUser(state.name)
            val platform = getPlatform()
            val greeting = foundUser?.let {
                getString(Res.string.hello_user_from_platform_name, it, platform.name)
            } ?: getString(Res.string.user_not_found)
            reduce {
                state.copy(
                    greeting = greeting,
                    showContent = true,
                )
            }
        } else {
            reduce {
                state.copy(showContent = false)
            }
        }
    }

    fun goToSecondScreenBtnClicked() = intent {
        postSideEffect(SideEffect.Navigate(Route.Second(name = state.name)))
    }
}