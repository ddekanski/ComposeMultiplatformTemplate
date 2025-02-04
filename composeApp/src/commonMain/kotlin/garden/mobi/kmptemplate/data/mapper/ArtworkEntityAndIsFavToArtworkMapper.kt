package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkEntity
import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkEntityAndIsFavToArtworkMapper : Mapper<Pair<ArtworkEntity, Boolean>, Artwork> {
    override fun Pair<ArtworkEntity, Boolean>.map(): Artwork {
        val artworkEntity = first
        val isFavorite = second
        return Artwork(
            id = artworkEntity.id,
            title = artworkEntity.title,
            imageUrl = artworkEntity.imageUrl,
            date = artworkEntity.date,
            artist = artworkEntity.artist,
            description = artworkEntity.description,
            type = artworkEntity.type,
            isFavorite = isFavorite,
        )
    }
}