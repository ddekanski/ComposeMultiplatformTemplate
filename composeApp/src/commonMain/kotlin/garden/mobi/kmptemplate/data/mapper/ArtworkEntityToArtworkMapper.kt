package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkEntity
import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkEntityToArtworkMapper : Mapper<ArtworkEntity, Artwork> {
    override fun ArtworkEntity.map(): Artwork = Artwork(
        id = id,
        title = title,
        imageUrl = imageUrl,
        date = date,
        artist = artist,
        description = description,
        type = type,
    )
}