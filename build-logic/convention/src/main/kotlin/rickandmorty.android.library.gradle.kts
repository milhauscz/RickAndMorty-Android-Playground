plugins {
    // AGP 9's built-in Kotlin support compiles Kotlin without a separate Kotlin Gradle plugin;
    // applying `org.jetbrains.kotlin.android` alongside it is now a hard error.
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("rickandmorty.lint")
}

val catalog = libs

android {
    namespace = androidNamespace
    compileSdk = androidCompileSdk

    defaultConfig {
        minSdk = androidMinSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    add("testImplementation", catalog.findLibrary("kotlin-test").get())
    // Binds kotlin.test's `@Test`/assertions to JUnit4; plain Android's unit test target does not
    // wire this in automatically.
    add("testImplementation", catalog.findLibrary("kotlin-testJunit").get())
    add("testImplementation", catalog.findLibrary("kotlinx-coroutines-test").get())
    add("testImplementation", catalog.findLibrary("junit").get())
}
