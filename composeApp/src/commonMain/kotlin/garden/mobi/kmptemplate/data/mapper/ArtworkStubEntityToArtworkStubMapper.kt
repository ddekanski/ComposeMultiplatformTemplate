package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkStubEntityToArtworkStubMapper : Mapper<ArtworkStubEntity, ArtworkStub> {
    override fun ArtworkStubEntity.map(): ArtworkStub = ArtworkStub(
        id = id,
        title = title,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
    )
}