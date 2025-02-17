package garden.mobi.kmptemplate.data.datasource

import Config
import garden.mobi.kmptemplate.data.dto.ApiResponseDto
import garden.mobi.kmptemplate.data.dto.ArtworkStubDto

class ArtworkStubApiDataSource(private val apiDataSource: ApiDataSource) {

    suspend fun getAllArtworks(): ApiResponseDto<List<ArtworkStubDto>> {
        return apiDataSource.getAllArtworks(url = getUrl(query = Config.ARTWORK_STUB_QUERY))
    }

    private fun getUrl(query: String) = "artworks/search?q=$query&limit=100&fields=id,title,image_id"
}