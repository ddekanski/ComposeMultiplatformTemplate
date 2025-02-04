package garden.mobi.kmptemplate.domain.repository

import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.util.DataResponse
import kotlinx.coroutines.flow.Flow

interface ArtworkRepository {
    suspend fun getArtwork(artworkId: String): Flow<DataResponse<Artwork>>
}