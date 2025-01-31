package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.mapper.ArtworkMapper
import garden.mobi.kmptemplate.data.mapper.ArtworkStubMapper
import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.util.map

class ArtworkRepositoryImpl(
    private val apiDataSource: ApiDataSource,
) : ArtworkRepository {

    override suspend fun getAllArtworkStubs(): List<ArtworkStub> {
        return apiDataSource
            .getAllArtworks()
            .data
            .map(ArtworkStubMapper())
    }

    override suspend fun getArtwork(artworkId: String): Artwork {
        return apiDataSource
            .getArtworkDetails(artworkId = artworkId)
            .data
            .map(ArtworkMapper())
    }
}