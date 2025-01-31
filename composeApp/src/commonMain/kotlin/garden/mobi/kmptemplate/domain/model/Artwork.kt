package garden.mobi.kmptemplate.domain.model

data class Artwork(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val date: String,
    val artist: String,
    val description: String?,
    val type: String,
)
