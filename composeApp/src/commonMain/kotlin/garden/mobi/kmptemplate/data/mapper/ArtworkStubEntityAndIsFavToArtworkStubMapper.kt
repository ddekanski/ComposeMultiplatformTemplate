package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkStubEntityAndIsFavToArtworkStubMapper : Mapper<Pair<ArtworkStubEntity, Boolean>, ArtworkStub> {
    override fun Pair<ArtworkStubEntity, Boolean>.map(): ArtworkStub {
        val artworkStubEntity = first
        val isFavorite = second
        return ArtworkStub(
            id = artworkStubEntity.id,
            title = artworkStubEntity.title,
            thumbnailUrl = artworkStubEntity.thumbnailUrl,
            imageUrl = artworkStubEntity.imageUrl,
            isFavorite = isFavorite,
        )
    }
}