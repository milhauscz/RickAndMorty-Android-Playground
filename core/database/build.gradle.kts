plugins {
    id("rickandmorty.android.library")
    id("rickandmorty.room")
}

dependencies {
    // DAO signatures expose PagingSource to consuming feature modules.
    api(libs.androidx.paging.common)
    // AppBuildConfig.isDebug drives allowDestructiveMigration.
    implementation(projects.core.common)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}
