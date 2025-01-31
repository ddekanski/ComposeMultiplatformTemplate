package garden.mobi.kmptemplate.data.mapper

import garden.mobi.kmptemplate.data.dto.ArtworkStubDto
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.util.Mapper

class ArtworkStubMapper : Mapper<ArtworkStubDto, ArtworkStub> {
    override fun ArtworkStubDto.map(): ArtworkStub = ArtworkStub(
        id = id,
        title = title,
        imageUrl = image_id?.let { "https://www.artic.edu/iiif/2/${image_id}/full/200,/0/default.jpg" }
    )
}