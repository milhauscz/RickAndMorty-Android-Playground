plugins {
    id("rickandmorty.android.library")
}

dependencies {
    // Result / DataError / Episode appear in EpisodeRepository's public API.
    api(projects.core.common)
    implementation(libs.kotlinx.coroutines.core)
}
