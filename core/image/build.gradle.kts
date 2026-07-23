plugins {
    id("rickandmorty.android.library")
}

dependencies {
    // ImageLoader / PlatformContext appear in createImageLoader()'s public signature.
    api(libs.coil.core)
    implementation(libs.coil.network)
    implementation(libs.ktor.client.cio)
}
