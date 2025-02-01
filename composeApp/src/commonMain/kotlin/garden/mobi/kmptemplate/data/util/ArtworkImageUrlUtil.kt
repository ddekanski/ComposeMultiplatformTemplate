package garden.mobi.kmptemplate.data.util

import garden.mobi.kmptemplate.data.dto.ArtworkStubDto

fun ArtworkStubDto.getThumbnailUrl() = image_id?.let { "https://www.artic.edu/iiif/2/${image_id}/full/200,/0/default.jpg" }

fun ArtworkStubDto.getImageUrl() = image_id?.let { "https://www.artic.edu/iiif/2/${image_id}/full/600,/0/default.jpg" }