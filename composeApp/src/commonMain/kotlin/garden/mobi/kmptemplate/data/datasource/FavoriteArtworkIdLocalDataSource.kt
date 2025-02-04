package garden.mobi.kmptemplate.data.datasource

import garden.mobi.kmptemplate.data.util.KeyValueStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class FavoriteArtworkIdLocalDataSource(private val keyValueStorage: KeyValueStorage) {

    private var favoriteArtworkIds: Set<String>
        get() = keyValueStorage.favoriteArtworkIdsJson?.let { Json.decodeFromString(it) } ?: emptySet()
        set(value) { keyValueStorage.favoriteArtworkIdsJson = Json.encodeToString(value) }

    fun addFavoriteArtworkId(id: String) {
        favoriteArtworkIds += id
    }

    fun removeFavoriteArtworkId(id: String) {
        favoriteArtworkIds -= id
    }

    fun getFavoriteArtworkIds(): Flow<Set<String>> {
        return keyValueStorage.favoriteArtworkIdsJsonFlow.map { it?.let { Json.decodeFromString(it) } ?: emptySet() }
    }

}