import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val javaVersion: JavaVersion by rootProject.extra
val flavor: String by project
val appIdSuffix = when (flavor) {
    "dev" -> ".dev"
    "prod" -> ""
    else -> throw IllegalStateException()
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.get()))
        }
    }
    
    listOf(
//todo excluded iosX64 and iosArm64 as a temporary solution to shorten the build process taking 20 minutes before
//        iosX64(),
//        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.sqlDelight.android)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelight.ios) // you also need to add "-lsqlite3" flag in XCode > Build > Other linker flags, see https://github.com/sqldelight/sqldelight/issues/1442#issuecomment-2119043745
        }

        commonMain.dependencies {
            implementation(project(":flavors:$flavor"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.compose.viewmodel.nav)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.orbit.mvi)

            implementation(libs.ktorfit.lib)
            implementation(libs.ktorfit.converters.response)
            implementation(libs.ktorfit.converters.call)
            implementation(libs.ktorfit.converters.flow)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)

            implementation(libs.richeditor.compose)

            implementation(libs.napier.logging)

            implementation(libs.sqlDelight.adapters)
            implementation(libs.sqlDelight.coroutines)

            implementation(libs.store)

            implementation(libs.multiplatform.settings.core)
            implementation(libs.multiplatform.settings.serialization)
            implementation(libs.multiplatform.settings.coroutines)
        }
    }
}

android {
    namespace = "garden.mobi.kmptemplate"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "garden.mobi.composemultiplatformtemplate"

        manifestPlaceholders["appName"] = when(flavor) {
            "prod" -> "CMP"
            "dev"  -> "CMP DEV"
            else -> throw IllegalStateException()
        }

        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            manifestPlaceholders["appNameSuffix"] = ""
            applicationIdSuffix = appIdSuffix
        }

        getByName("debug") {
            isMinifyEnabled = false
            manifestPlaceholders["appNameSuffix"] = " Debug"
            //noinspection GradlePath
            applicationIdSuffix = "$appIdSuffix.debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get())
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

sqldelight {
    databases {
        register("Database") {
            packageName = "garden.mobi.kmptemplate.data.db"
            srcDirs("src/commonMain/kotlin")
        }
    }
}

