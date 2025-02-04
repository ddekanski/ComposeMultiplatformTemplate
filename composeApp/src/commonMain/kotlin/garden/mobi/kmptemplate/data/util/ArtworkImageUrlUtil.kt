package garden.mobi.kmptemplate.data.util

fun String?.getThumbnailUrl() = this?.let { "https://www.artic.edu/iiif/2/${this}/full/200,/0/default.jpg" }

fun String?.getImageUrl() = this?.let { "https://www.artic.edu/iiif/2/${this}/full/600,/0/default.jpg" }