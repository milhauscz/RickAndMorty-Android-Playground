plugins {
    id("rickandmorty.android.library")
    id("rickandmorty.android.compose")
}

dependencies {
    // DataError appears in toMessageRes()'s public signature.
    api(projects.core.common)
    // HttpClientException is matched in Throwable.toMessageRes().
    implementation(projects.core.network)
}
