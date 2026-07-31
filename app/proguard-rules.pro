# Kotlin / coroutines
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature
-dontwarn kotlinx.coroutines.**

# kotlinx.serialization — keep @Serializable models used by Ktor and Navigation routes.
-keepattributes RuntimeVisibleAnnotations, AnnotationDefault
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keep @kotlinx.serialization.Serializable class ** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}

# Ktor
-keep class io.ktor.** { *; }
-keepclassmembers class io.ktor.** { *; }
-dontwarn io.ktor.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-dontwarn androidx.room.paging.**

# Koin
-keep class org.koin.** { *; }
-keep class * extends org.koin.core.module.Module

# Coil
-keep class coil3.** { *; }
-dontwarn coil3.**

# Project DTOs, entities, and navigation routes.
-keep class cz.cernilovsky.android.rickandmorty.**.remote.** { *; }
-keep class cz.cernilovsky.android.rickandmorty.**.data.local.** { *; }
-keep @kotlinx.serialization.Serializable class cz.cernilovsky.android.rickandmorty.navigation.** { *; }

# Paging
-dontwarn androidx.paging.**
