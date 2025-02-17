package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.data.datasource.ArtworkStubApiDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkStubLocalDataSource
import garden.mobi.kmptemplate.data.datasource.FavoriteArtworkIdLocalDataSource
import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.data.mapper.ArtworkStubDtoToArtworkStubEntityMapper
import garden.mobi.kmptemplate.data.mapper.ArtworkStubEntityAndIsFavToArtworkStubMapper
import garden.mobi.kmptemplate.data.util.mapToDataResponse
import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.repository.ArtworkStubRepository
import garden.mobi.kmptemplate.domain.util.DataResponse
import garden.mobi.kmptemplate.domain.util.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.fresh

private const val DUMMY_KEY = ""

class ArtworkStubRepositoryImpl(
    private val apiDataSource: ArtworkStubApiDataSource,
    private val localDataSource: ArtworkStubLocalDataSource,
    private val favoriteArtworkIdLocalDataSource: FavoriteArtworkIdLocalDataSource,
) : ArtworkStubRepository {

    private val artworkStubDtoToArtworkStubEntityMapper = ArtworkStubDtoToArtworkStubEntityMapper()
    private val artworkStubEntityAndIsFavToArtworkStubMapper = ArtworkStubEntityAndIsFavToArtworkStubMapper()

    private val fetcher: Fetcher<Any, List<ArtworkStubEntity>> = Fetcher.of { _ ->
        apiDataSource.getAllArtworks().data.map(artworkStubDtoToArtworkStubEntityMapper)
    }

    private val sourceOfTruth: SourceOfTruth<Any, List<ArtworkStubEntity>, List<ArtworkStub>> = SourceOfTruth.of(
        reader = { _ ->
            localDataSource
                .getArtworkStubs(Dispatchers.IO)
                .combine(
                    flow = favoriteArtworkIdLocalDataSource.getFavoriteArtworkIds(),
                    transform = { artworkStubs, favoriteArtworkIds -> Pair(artworkStubs, favoriteArtworkIds) }
                )
                .map { (artworkStubs, favoriteArtworkIds) ->
                    artworkStubs.map { artworkStub ->
                        Pair(artworkStub, favoriteArtworkIds.contains(artworkStub.id)).map(artworkStubEntityAndIsFavToArtworkStubMapper)
                    }
                }
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

    override fun getFavoriteArtworkStubs(): Flow<DataResponse<List<ArtworkStub>>> {
        return getAllArtworkStubs()
            .map { dataResponse ->
                if (dataResponse is DataResponse.Data) {
                    dataResponse.copy(data = dataResponse.data.filter { it.isFavorite })
                } else dataResponse
            }
    }

    override suspend fun refresh() {
        store.fresh(key = DUMMY_KEY)
    }

    override fun addToFavorites(artworkId: String) {
        favoriteArtworkIdLocalDataSource.addFavoriteArtworkId(artworkId)
    }

    override fun removeFromFavorites(artworkId: String) {
        favoriteArtworkIdLocalDataSource.removeFavoriteArtworkId(artworkId)
    }
}