package garden.mobi.kmptemplate.data.dto

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class ArtworkDto(
    val id: String,
    val title: String,
    val image_id: String?,
    val date_display: String,
    val artist_display: String,
    val description: String?,
    val artwork_type_title: String,
)
