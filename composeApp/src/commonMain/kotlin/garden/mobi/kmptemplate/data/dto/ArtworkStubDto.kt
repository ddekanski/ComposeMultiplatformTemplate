package garden.mobi.kmptemplate.data.dto

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class ArtworkStubDto(
    val id: String,
    val title: String,
    val image_id: String?,
)
