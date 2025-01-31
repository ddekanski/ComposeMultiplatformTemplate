package garden.mobi.kmptemplate.domain.repository

import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.model.ArtworkStub

interface ArtworkRepository {
    suspend fun getAllArtworkStubs(): List<ArtworkStub>
    suspend fun getArtwork(artworkId: String): Artwork
}