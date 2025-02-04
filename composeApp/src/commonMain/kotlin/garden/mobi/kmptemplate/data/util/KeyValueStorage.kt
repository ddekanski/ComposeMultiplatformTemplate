package garden.mobi.kmptemplate.data.util

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.nullableString
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class KeyValueStorage(
    settings: ObservableSettings,
    private val flowSettings: FlowSettings,
) {

    enum class Keys {
        FavoriteArtworkIds,
    }

    var favoriteArtworkIdsJson: String? by settings.nullableString(key = Keys.FavoriteArtworkIds.name)
    val favoriteArtworkIdsJsonFlow: Flow<String?> get() = flowSettings.getStringOrNullFlow(key = Keys.FavoriteArtworkIds.name)

}