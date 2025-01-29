package garden.mobi.kmptemplate.di

import org.koin.dsl.koinConfiguration

actual fun nativeConfig() = koinConfiguration {
    printLogger()
}