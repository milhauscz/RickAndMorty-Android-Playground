plugins {
    id("rickandmorty.android.library")
}

dependencies {
    api(projects.core.common)
    api(projects.feature.episode.api)
    api(projects.feature.location.api)
    // PagingData appears in CharactersRepository's public API.
    api(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)
}
