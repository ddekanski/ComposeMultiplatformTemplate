package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.mapper.ArtworkMapper
import garden.mobi.kmptemplate.data.mapper.ArtworkStubMapper
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.util.map
import kotlinx.coroutines.flow.flow

class ArtworkRepositoryImpl(
    private val apiDataSource: ApiDataSource,
) : ArtworkRepository {

    override suspend fun getAllArtworkStubs() = flow {
        emit(
            apiDataSource
            .getAllArtworks()
            .data
            .map(ArtworkStubMapper())
        )
    }

    override suspend fun getArtwork(artworkId: String) = flow {
        emit(
            apiDataSource
            .getArtworkDetails(artworkId = artworkId)
            .data
            .map(ArtworkMapper())
        )
    }
}