package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkEntity
import garden.mobi.kmptemplate.data.dto.ArtworkDto
import garden.mobi.kmptemplate.data.util.getImageUrl
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkDtoToArtworkEntityMapper : Mapper<ArtworkDto, ArtworkEntity> {
    override fun ArtworkDto.map(): ArtworkEntity = ArtworkEntity(
        id = id,
        title = title,
        imageUrl = image_id.getImageUrl(),
        date = date_display,
        artist = artist_display,
        description = description,
        type = artwork_type_title,
    )
}