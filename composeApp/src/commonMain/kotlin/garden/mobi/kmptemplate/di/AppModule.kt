package garden.mobi.kmptemplate.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import de.jensklingenberg.ktorfit.converter.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import garden.mobi.kmptemplate.data.datasource.ApiDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkLocalDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkStubApiDataSource
import garden.mobi.kmptemplate.data.datasource.ArtworkStubLocalDataSource
import garden.mobi.kmptemplate.data.datasource.FavoriteArtworkIdLocalDataSource
import garden.mobi.kmptemplate.data.datasource.createApiDataSource
import garden.mobi.kmptemplate.data.db.ArtworkEntityQueries
import garden.mobi.kmptemplate.data.db.ArtworkStubEntityQueries
import garden.mobi.kmptemplate.data.repository.ArtworkRepositoryImpl
import garden.mobi.kmptemplate.data.repository.ArtworkStubRepositoryImpl
import garden.mobi.kmptemplate.data.repository.UserRepositoryImpl
import garden.mobi.kmptemplate.data.util.KeyValueStorage
import garden.mobi.kmptemplate.domain.repository.ArtworkRepository
import garden.mobi.kmptemplate.domain.repository.ArtworkStubRepository
import garden.mobi.kmptemplate.domain.repository.UserRepository
import garden.mobi.kmptemplate.view.artworkDetails.ArtworkDetailsViewModel
import garden.mobi.kmptemplate.view.artworkList.ArtworkListViewModel
import garden.mobi.kmptemplate.view.favArtworkList.FavArtworkListViewModel
import garden.mobi.kmptemplate.view.greeting.GreetingViewModel
import garden.mobi.kmptemplate.view.second.SecondViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
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
    modules(
        platformModule(logEnabled = true),
        appModule,
    )
}

expect fun platformModule(logEnabled: Boolean): Module

@OptIn(ExperimentalSettingsApi::class)
val appModule = module {
    viewModelOf(::GreetingViewModel)
    viewModelOf(::SecondViewModel)
    viewModelOf(::ArtworkListViewModel)
    viewModelOf(::FavArtworkListViewModel)
    viewModelOf(::ArtworkDetailsViewModel)

    singleOf(::ArtworkStubEntityQueries)
    singleOf(::ArtworkEntityQueries)

    singleOf(::ArtworkStubApiDataSource)

    singleOf(::ArtworkStubLocalDataSource)
    singleOf(::ArtworkLocalDataSource)
    singleOf(::FavoriteArtworkIdLocalDataSource)

    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::ArtworkRepositoryImpl) { bind<ArtworkRepository>() }
    singleOf(::ArtworkStubRepositoryImpl) { bind<ArtworkStubRepository>() }

    single<ObservableSettings> { Settings() as ObservableSettings }
    single<FlowSettings> { get<ObservableSettings>().toFlowSettings() }

    singleOf(::KeyValueStorage)


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

                        install(Logging) {
                            logger = object: Logger {
                                override fun log(message: String) {
                                    garden.mobi.kmptemplate.Logger.d(message)
                                }
                            }
                            level = LogLevel.ALL
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