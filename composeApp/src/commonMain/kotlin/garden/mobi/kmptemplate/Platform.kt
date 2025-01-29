package garden.mobi.kmptemplate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform