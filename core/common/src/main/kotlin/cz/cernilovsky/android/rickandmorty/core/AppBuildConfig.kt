package cz.cernilovsky.android.rickandmorty.core

/**
 * App-wide build information, provided via [cz.cernilovsky.android.rickandmorty.core.di.commonPlatformModule].
 *
 * @property isDebug whether this is a debuggable build (`FLAG_DEBUGGABLE`).
 */
data class AppBuildConfig(
    val isDebug: Boolean,
)
