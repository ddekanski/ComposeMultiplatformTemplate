package garden.mobi.kmptemplate.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration
import garden.mobi.kmptemplate.MainApplication
import garden.mobi.kmptemplate.data.db.Database
import org.koin.dsl.module

actual fun nativeConfig() = koinConfiguration {
    androidLogger()
    androidContext(MainApplication.instance ?: error("No Android application context set"))
}

actual fun platformModule(logEnabled: Boolean) = module {
    single<SqlDriver> {
        AndroidSqliteDriver(Database.Schema, get(), "Database.db")
    }
}