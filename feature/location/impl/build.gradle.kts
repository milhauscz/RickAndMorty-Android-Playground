plugins {
    id("rickandmorty.android.library")
}

dependencies {
    implementation(projects.feature.location.api)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(libs.koin.core)
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.ktor.client.mock)
}
