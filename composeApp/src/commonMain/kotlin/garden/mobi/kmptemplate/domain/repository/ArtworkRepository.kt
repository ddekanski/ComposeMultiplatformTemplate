package garden.mobi.kmptemplate.domain.repository

import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import kotlinx.coroutines.flow.Flow

interface ArtworkRepository {
    suspend fun getAllArtworkStubs(): Flow<List<ArtworkStub>>
    suspend fun getArtwork(artworkId: String): Flow<Artwork>
}