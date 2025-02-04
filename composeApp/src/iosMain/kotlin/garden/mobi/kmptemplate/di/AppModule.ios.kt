package garden.mobi.kmptemplate.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import garden.mobi.kmptemplate.data.db.Database
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

actual fun nativeConfig() = koinConfiguration {
    printLogger()
}

actual fun platformModule(logEnabled: Boolean) = module {
    single<SqlDriver> { NativeSqliteDriver(Database.Schema, "Database.db") }
}