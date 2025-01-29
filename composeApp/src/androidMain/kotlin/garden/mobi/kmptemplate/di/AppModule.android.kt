package garden.mobi.kmptemplate.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration
import garden.mobi.kmptemplate.MainApplication

actual fun nativeConfig() = koinConfiguration {
    androidLogger()
    androidContext(MainApplication.instance ?: error("No Android application context set"))
}