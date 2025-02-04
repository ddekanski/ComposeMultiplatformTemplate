package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.data.dto.ArtworkStubDto
import garden.mobi.kmptemplate.data.util.getImageUrl
import garden.mobi.kmptemplate.data.util.getThumbnailUrl
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkStubDtoToArtworkStubEntityMapper : Mapper<ArtworkStubDto, ArtworkStubEntity> {
    override fun ArtworkStubDto.map(): ArtworkStubEntity = ArtworkStubEntity(
        id = id,
        title = title,
        thumbnailUrl = image_id.getThumbnailUrl(),
        imageUrl = image_id.getImageUrl(),
    )
}