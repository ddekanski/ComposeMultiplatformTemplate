package garden.mobi.kmptemplate.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import garden.mobi.kmptemplate.data.db.ArtworkStubEntity
import garden.mobi.kmptemplate.data.db.ArtworkStubEntityQueries
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ArtworkStubLocalDataSource(private val artworkStubEntityQueries: ArtworkStubEntityQueries) {

    fun getArtworkStubs(coroutineContext: CoroutineContext): Flow<List<ArtworkStubEntity>> {
        return artworkStubEntityQueries.selectAll().asFlow().mapToList(coroutineContext)
    }

    fun upsertArtworkStubs(entities: List<ArtworkStubEntity>) {
        artworkStubEntityQueries.transaction {
            entities.forEach { artworkStubEntityQueries.insertOrUpdate(it) }
        }
    }
}