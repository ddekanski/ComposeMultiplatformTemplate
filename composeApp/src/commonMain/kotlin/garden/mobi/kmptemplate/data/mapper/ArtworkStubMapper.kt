package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.dto.ArtworkStubDto
import garden.mobi.kmptemplate.data.util.getImageUrl
import garden.mobi.kmptemplate.data.util.getThumbnailUrl
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkStubMapper : Mapper<ArtworkStubDto, ArtworkStub> {
    override fun ArtworkStubDto.map(): ArtworkStub = ArtworkStub(
        id = id,
        title = title,
        thumbnailUrl = getThumbnailUrl(),
        imageUrl = getImageUrl(),
    )
}