package garden.mobi.kmptemplate.di

import de.jensklingenberg.ktorfit.converter.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.datasource.createApiDataSource
import garden.mobi.kmptemplate.data.repository.ArtworkRepositoryImpl
import garden.mobi.kmptemplate.data.repository.UserRepositoryImpl
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.repository.UserRepository
import garden.mobi.kmptemplate.view.artworkList.ArtworkListViewModel
import garden.mobi.kmptemplate.view.artworkDetails.ArtworkDetailsViewModel
import garden.mobi.kmptemplate.view.greeting.GreetingViewModel
import garden.mobi.kmptemplate.view.second.SecondViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

expect fun nativeConfig() : KoinConfiguration

val koinConfig = koinConfiguration {
    includes(nativeConfig())
    modules(appModule)
}

val appModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::ArtworkRepositoryImpl) { bind<ArtworkRepository>() }

    viewModelOf(::GreetingViewModel)
    viewModelOf(::SecondViewModel)
    viewModelOf(::ArtworkListViewModel)
    viewModelOf(::ArtworkDetailsViewModel)

    single<ApiDataSource> {
        val ktorfit =
            ktorfit {
                baseUrl(ApiDataSource.BASE_URL)

                httpClient(
                    HttpClient {
                        install(ContentNegotiation) {
                            json(
                                Json {
                                    isLenient = true
                                    ignoreUnknownKeys = true
                                }
                            )
                        }
                    }
                )

                converterFactories(
                    FlowConverterFactory(),
                    CallConverterFactory()
                )
            }

        ktorfit.createApiDataSource()
    }
}