package garden.mobi.kmptemplate.data.util

import garden.mobi.kmptemplate.domain.util.DataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.mobilenativefoundation.store.store5.StoreReadResponse

fun <T> Flow<StoreReadResponse<T>>.mapToDataResponse() = mapNotNull { storeResponse: StoreReadResponse<T> ->
    when (storeResponse) {
        is StoreReadResponse.Loading -> DataResponse.Loading
        is StoreReadResponse.Data -> DataResponse.Data(storeResponse.value)
        is StoreReadResponse.Error.Exception -> DataResponse.Error(throwable = storeResponse.error)
        else -> null
    }
}
