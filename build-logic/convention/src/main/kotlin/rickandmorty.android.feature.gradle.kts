plugins {
    id("rickandmorty.android.library")
    id("rickandmorty.android.compose")
}

val catalog = libs

dependencies {
    add("implementation", catalog.findLibrary("koin-core").get())
    add("implementation", catalog.findLibrary("koin-android").get())
    add("implementation", catalog.findLibrary("koin-compose").get())
    add("implementation", catalog.findLibrary("koin-compose-viewmodel").get())
    add("implementation", catalog.findLibrary("androidx-lifecycle-viewmodelCompose").get())
    add("implementation", catalog.findLibrary("androidx-lifecycle-runtimeCompose").get())

    add("testImplementation", catalog.findLibrary("robolectric").get())
    add("testImplementation", catalog.findLibrary("compose-ui-test-junit4").get())
}
