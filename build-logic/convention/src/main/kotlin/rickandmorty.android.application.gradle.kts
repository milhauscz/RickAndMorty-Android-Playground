plugins {
    // AGP 9's built-in Kotlin support compiles Kotlin without a separate Kotlin Gradle plugin;
    // applying `org.jetbrains.kotlin.android` alongside it is now a hard error.
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "cz.cernilovsky.android.rickandmorty"
    compileSdk = androidCompileSdk

    defaultConfig {
        applicationId = "cz.cernilovsky.android.rickandmorty"
        minSdk = androidMinSdk
        targetSdk = androidTargetSdk
        // versionCode/versionName live in app/build.gradle.kts, not here: they're this specific
        // app's release identity, not something every consumer of this convention plugin shares.
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    val catalog = libs
    val composeBom = platform(catalog.findLibrary("compose-bom").get())
    add("implementation", composeBom)

    add("implementation", catalog.findLibrary("androidx-activity-compose").get())
    add("implementation", catalog.findLibrary("compose-uiToolingPreview").get())
    add("debugImplementation", catalog.findLibrary("compose-uiTooling").get())
}
