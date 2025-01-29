package garden.mobi.kmptemplate.view

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data class Greeting(val name: String) : Route()

}
