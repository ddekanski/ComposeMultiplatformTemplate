package garden.mobi.kmptemplate.view

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object Greeting : Route()

    @Serializable
    data class Second(val name: String) : Route()

    @Serializable
    data object ArtworkList : Route()

    @Serializable
    data object FavoriteArtworkList : Route()

    @Serializable
    data class ArtworkDetails(
        val artworkId: String,
        val imageUrl: String? = null,
        val imagePlaceholderMemoryCacheKey: String? = null,
    ) : Route()

}
