plugins {
    id("rickandmorty.android.library")
}

dependencies {
    // Result / DataError / Location appear in LocationRepository's public API.
    api(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(libs.koin.core)
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.ktor.client.mock)
}
