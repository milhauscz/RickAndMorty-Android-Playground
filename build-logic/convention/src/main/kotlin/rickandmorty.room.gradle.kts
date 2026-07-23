plugins {
    // Declared so the `dependencies { }` accessors are generated for this precompiled script.
    // Applied idempotently alongside `rickandmorty.android.library`.
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("androidx.room")
}

val catalog = libs

dependencies {
    add("implementation", catalog.findLibrary("androidx-room-runtime").get())
    add("implementation", catalog.findLibrary("androidx-room-paging").get())
    add("implementation", catalog.findLibrary("androidx-sqlite-bundled").get())
    add("ksp", catalog.findLibrary("androidx-room-compiler").get())
}

room {
    schemaDirectory("$projectDir/schemas")
}
