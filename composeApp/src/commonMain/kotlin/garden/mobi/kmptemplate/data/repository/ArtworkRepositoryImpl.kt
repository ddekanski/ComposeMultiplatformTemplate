package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkLocalDataSource
import garden.mobi.kmptemplate.data.db.ArtworkEntity
import garden.mobi.kmptemplate.data.mapper.ArtworkDtoToArtworkEntityMapper
import garden.mobi.kmptemplate.data.mapper.ArtworkEntityToArtworkMapper
import garden.mobi.kmptemplate.data.util.mapToDataResponse
import garden.mobi.kmptemplate.domain.model.Artwork
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.util.DataResponse
import garden.mobi.kmptemplate.domain.util.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest

class ArtworkRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val localDataSource: ArtworkLocalDataSource,
) : ArtworkRepository {

    private val artworkDtoToArtworkEntityMapper = ArtworkDtoToArtworkEntityMapper()
    private val artworkEntityToArtworkMapper = ArtworkEntityToArtworkMapper()

    private val fetcher: Fetcher<String, ArtworkEntity> = Fetcher.of { artworkId ->
        apiDataSource.getArtworkDetails(artworkId).data.map(artworkDtoToArtworkEntityMapper)
    }

    private val sourceOfTruth: SourceOfTruth<String, ArtworkEntity, Artwork> = SourceOfTruth.of(
        reader = { artworkId ->
            localDataSource
                .getArtworkById(artworkId, Dispatchers.IO)
                .map { it?.map(artworkEntityToArtworkMapper) }
        },
        writer = { _, artworkEntity ->
            localDataSource.upsertArtwork(artworkEntity)
        }
    )

    private val store: Store<String, Artwork> = StoreBuilder
        .from(
            fetcher = fetcher,
            sourceOfTruth = sourceOfTruth,
        )
        .build()

    override suspend fun getArtwork(artworkId: String): Flow<DataResponse<Artwork>> {
        return store
            .stream(StoreReadRequest.cached(key = artworkId, refresh = false))
            .mapToDataResponse()
    }
}