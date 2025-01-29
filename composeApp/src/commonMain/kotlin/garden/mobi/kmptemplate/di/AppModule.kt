package garden.mobi.kmptemplate.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import garden.mobi.kmptemplate.view.greeting.GreetingViewModel
import garden.mobi.kmptemplate.view.second.SecondViewModel
import garden.mobi.kmptemplate.domain.repository.UserRepository
import garden.mobi.kmptemplate.data.repository.UserRepositoryImpl

expect fun nativeConfig() : KoinConfiguration

val koinConfig = koinConfiguration {
    includes(nativeConfig())
    modules(appModule)
}

val appModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    viewModelOf(::GreetingViewModel)
    viewModelOf(::SecondViewModel)
}