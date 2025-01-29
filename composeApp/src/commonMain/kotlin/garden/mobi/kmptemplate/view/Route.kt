package garden.mobi.kmptemplate.view

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object Greeting : Route()

    @Serializable
    data class Second(val name: String) : Route()

}
