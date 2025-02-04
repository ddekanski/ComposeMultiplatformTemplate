package garden.mobi.kmptemplate.domain.model

data class ArtworkStub(
    val id: String,
    val title: String,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)
