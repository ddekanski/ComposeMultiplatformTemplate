package garden.mobi.kmptemplate.domain.repository

import garden.mobi.kmptemplate.domain.model.ArtworkStub
import garden.mobi.kmptemplate.domain.util.DataResponse
import kotlinx.coroutines.flow.Flow

interface ArtworkStubRepository {
    fun getAllArtworkStubs(): Flow<DataResponse<List<ArtworkStub>>>
    fun triggerRefresh()
}