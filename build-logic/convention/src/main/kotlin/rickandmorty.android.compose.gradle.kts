import com.android.build.api.dsl.CommonExtension

plugins {
    // Deliberately no com.android.library/application id here: this plugin is applied *after*
    // rickandmorty.android.library or rickandmorty.android.application in every consumer, and
    // declaring either AGP plugin again would conflict with whichever one the consumer already
    // applied. CommonExtension (implemented by both LibraryExtension and ApplicationExtension)
    // gives typed `android { }` access without picking one.
    id("org.jetbrains.kotlin.plugin.compose")
}

val catalog = libs

extensions.configure<CommonExtension> {
    buildFeatures.compose = true
}

dependencies {
    val composeBom = platform(catalog.findLibrary("compose-bom").get())
    add("implementation", composeBom)
    add("androidTestImplementation", composeBom)

    add("implementation", catalog.findLibrary("compose-runtime").get())
    add("implementation", catalog.findLibrary("compose-foundation").get())
    add("implementation", catalog.findLibrary("compose-material3").get())
    add("implementation", catalog.findLibrary("compose-ui").get())
    add("implementation", catalog.findLibrary("compose-uiToolingPreview").get())
    add("debugImplementation", catalog.findLibrary("compose-uiTooling").get())
}

configurations.all {
    resolutionStrategy {
        // The catalog's compose-bom tracks the latest BOM, which currently recommends a
        // material3 1.5.0 alpha whose Carousel opt-in marker (ExperimentalMaterial3ExpressiveApi)
        // is library-internal - unusable from here. Pin the last stable release instead, where
        // that annotation (and the emphasized Typography variants) are public/experimental as
        // expected.
        force("androidx.compose.material3:material3:1.4.0")
    }
}
