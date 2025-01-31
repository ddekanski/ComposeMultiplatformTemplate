package garden.mobi.kmptemplate.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto<T>(
    val data: T,
)