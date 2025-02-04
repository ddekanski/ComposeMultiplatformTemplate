package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.Logger
import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkStubLocalDataSource
import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.data.mapper.ArtworkStubDtoToArtworkStubEntityMapper
import garden.mobi.kmptemplate.data.mapper.ArtworkStubEntityToArtworkStubMapper
import garden.mobi.kmptemplate.data.util.mapToDataResponse
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.repository.ArtworkStubRepository
import garden.mobi.kmptemplate.domain.util.DataResponse
import garden.mobi.kmptemplate.domain.util.GlobalDefaultBackgroundScope
import garden.mobi.kmptemplate.domain.util.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.fresh

private const val DUMMY_KEY = ""

class ArtworkStubRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val localDataSource: ArtworkStubLocalDataSource,
) : ArtworkStubRepository {

    private val artworkStubDtoToArtworkStubEntityMapper = ArtworkStubDtoToArtworkStubEntityMapper()
    private val artworkStubEntityToArtworkStubMapper = ArtworkStubEntityToArtworkStubMapper()

    private val fetcher: Fetcher<Any, List<ArtworkStubEntity>> = Fetcher.of { _ ->
        apiDataSource.getAllArtworks().data.map(artworkStubDtoToArtworkStubEntityMapper)
    }

    private val sourceOfTruth: SourceOfTruth<Any, List<ArtworkStubEntity>, List<ArtworkStub>> = SourceOfTruth.of(
        reader = { _ ->
            localDataSource
                .getArtworkStubs(Dispatchers.IO)
                .map { it.map(artworkStubEntityToArtworkStubMapper) }
                .map { it.ifEmpty { null } }
        },
        writer = { _, artworkStubEntities ->
            localDataSource.upsertArtworkStubs(artworkStubEntities)
        }
    )

    private val store: Store<Any, List<ArtworkStub>> = StoreBuilder
        .from(
            fetcher = fetcher,
            sourceOfTruth = sourceOfTruth,
        )
        .build()

    override fun getAllArtworkStubs(): Flow<DataResponse<List<ArtworkStub>>> {
        return store
            .stream(StoreReadRequest.cached(key = DUMMY_KEY, refresh = false))
            .mapToDataResponse()
    }

    override fun triggerRefresh() {
        GlobalDefaultBackgroundScope().launch {
            try {
                store.fresh(key = DUMMY_KEY)
            } catch (throwable: Throwable) {
                Logger.e(message = "Cannot refresh artwork stubs", throwable = throwable)
            }
        }
    }
}