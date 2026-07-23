plugins {
    id("rickandmorty.android.feature")
}

dependencies {
    // PredictiveBackHandler in CharacterListDetailScreen.
    implementation(libs.androidx.activity.compose)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.designsystem)
    implementation(projects.feature.episode)
    implementation(projects.feature.location)
    implementation(libs.coil.compose)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.compose)
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.window.core)
    implementation(libs.compose.adaptive)
    implementation(libs.compose.adaptive.layout)
    implementation(libs.compose.adaptive.navigation)

    testImplementation(libs.ktor.client.mock)
}
