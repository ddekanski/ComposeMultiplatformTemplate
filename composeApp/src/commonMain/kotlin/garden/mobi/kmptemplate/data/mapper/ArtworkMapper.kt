package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.dto.ArtworkDto
import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkMapper : Mapper<ArtworkDto, Artwork> {
    override fun ArtworkDto.map(): Artwork = Artwork(
        id = id,
        title = title,
        imageUrl = image_id?.let { "https://www.artic.edu/iiif/2/${image_id}/full/600,/0/default.jpg" },
        date = date_display,
        artist = artist_display,
        description = description,
        type = artwork_type_title,
    )
}