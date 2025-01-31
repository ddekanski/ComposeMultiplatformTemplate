package garden.mobi.kmptemplate.data.datasource

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import garden.mobi.kmptemplate.data.dto.ApiResponseDto
import garden.mobi.kmptemplate.data.dto.ArtworkDto
import garden.mobi.kmptemplate.data.dto.ArtworkStubDto

interface ApiDataSource {

    companion object {
        const val BASE_URL = "https://api.artic.edu/api/v1/"
    }

    @GET("artworks?limit=50&fields=id,title,image_id")
    suspend fun getAllArtworks(): ApiResponseDto<List<ArtworkStubDto>>

    @GET("artworks/{id}?fields=id,title,image_id,date_display,artist_display,description,artwork_type_title")
    suspend fun getArtworkDetails(@Path("id") artworkId: String): ApiResponseDto<ArtworkDto>

}