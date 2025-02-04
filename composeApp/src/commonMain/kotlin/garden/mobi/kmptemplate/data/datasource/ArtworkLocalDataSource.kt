package garden.mobi.kmptemplate.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import garden.mobi.kmptemplate.data.db.ArtworkEntity
import garden.mobi.kmptemplate.data.db.ArtworkEntityQueries
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ArtworkLocalDataSource(private val artworkEntityQueries: ArtworkEntityQueries) {

    fun getArtworkById(id: String, coroutineContext: CoroutineContext): Flow<ArtworkEntity?> {
        return artworkEntityQueries.selectById(id).asFlow().mapToOneOrNull(coroutineContext)
    }

    fun upsertArtwork(entity: ArtworkEntity) {
        artworkEntityQueries.insertOrUpdate(entity)
    }
}