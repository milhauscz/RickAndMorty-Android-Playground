plugins {
    id("rickandmorty.android.application")
    id("rickandmorty.android.compose")
    alias(libs.plugins.kotlinxSerialization)
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)
    implementation(projects.core.image)
    implementation(projects.feature.episode.impl)
    implementation(projects.feature.location.impl)
    implementation(projects.feature.characters.impl)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    // Coil singleton factory + NavHost + type-safe serializable routes.
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.window.core)
    implementation(libs.androidx.concurrent.futures)

    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
}
