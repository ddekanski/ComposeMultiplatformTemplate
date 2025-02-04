package garden.mobi.kmptemplate.domain.util

sealed interface DataResponse<out T> {
    data object Loading : DataResponse<Nothing>
    data class Data<T>(val data: T) : DataResponse<T>
    data class Error(val throwable: Throwable, val message: String? = null) : DataResponse<Nothing>
}