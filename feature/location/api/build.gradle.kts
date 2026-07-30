plugins {
    id("rickandmorty.android.library")
}

dependencies {
    // Result / DataError / Location appear in LocationRepository's public API.
    api(projects.core.common)
    implementation(libs.kotlinx.coroutines.core)
}
