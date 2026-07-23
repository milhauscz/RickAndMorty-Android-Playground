plugins {
    id("rickandmorty.android.library")
}

dependencies {
    // AppBuildConfig is provided through Koin (commonPlatformModule).
    implementation(libs.koin.core)
}
