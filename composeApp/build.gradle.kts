import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)

    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")

    room {
        schemaDirectory("$projectDir/schemas")
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.play.services.location)

//            implementation ("com.google.accompanist:accompanist-permissions:0.32.0")

            implementation(libs.compass.geocoder.mobile)
            implementation(libs.core.splashscreen)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.libphonenumber) ////phone number format
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.core)
            implementation(libs.bundles.coil)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.bundles.ktor)

            implementation(libs.qr.kit)
            // Enables FileKit without Compose dependencies
//            https://github.com/vinceglb/FileKit
//            implementation("io.github.vinceglb:filekit-core:0.8.8")

            // Enables FileKit with Composable utilities
            implementation(libs.filekit.compose)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)


//            api("dev.icerock.moko:permissions:0.18.1")
//            api("dev.icerock.moko:permissions-compose:0.18.1")
            implementation(libs.compass.geocoder)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            implementation(libs.ktor.client.okhttp)

            implementation(libs.compass.geocoder.web.googlemaps)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)

            implementation(libs.compass.geocoder.mobile)
        }
        dependencies {
            ksp(libs.androidx.room.compiler)
        }
    }
}

android {
    namespace = "com.fraggeil.ticketator"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.fraggeil.ticketator"
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
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            isEnabled = false // TODO TODO
            optimize.set(true)
            obfuscate.set(false)
        }

        mainClass = "com.fraggeil.ticketator.MainKt"

        nativeDistributions {
            includeAllModules = true
//            outputBaseDir.set(project.buildDir.resolve("customOutputDir"))

            targetFormats(TargetFormat.Exe, TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Ticketator"
            packageVersion = "1.0.0"
            description = "Ticketator: Bus ticket booking app "
            vendor = "Fraggeil"
            copyright = "© 2024 Sergio Belda. Licensed under the Apache License." // todo change
//            licenseFile.set(project.file("../LICENSE")) //todo change
            licenseFile.set(project.file("./launcher_icons/LICENSE")) //todo change
            macOS{
                bundleID = "com.fraggeil.ticketator"
                iconFile.set(project.file("./launcher_icons/macos_icon.icns"))
                dockName = "Ticketator"
            }
            windows{
                iconFile.set(project.file("./launcher_icons/windows_icon.ico"))
                menuGroup = "Ticketator"
                shortcut = true // create shortcut on desktop
                dirChooser = true // choose installation folder
                console = false // run app as console app // if true can see logs in terminal
                perUserInstall = true // if true it will be installed in the local user else programFiles
                upgradeUuid = "3af5f6a6-3fbe-465b-af40-549cd7a9c08d"
            }
            linux {
                iconFile.set(project.file("./launcher_icons/linux_icon.png"))
            }
        }
    }
}
