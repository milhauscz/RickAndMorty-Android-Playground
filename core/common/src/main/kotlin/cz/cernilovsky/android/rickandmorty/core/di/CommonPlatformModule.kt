package cz.cernilovsky.android.rickandmorty.core.di

import android.content.Context
import android.content.pm.ApplicationInfo
import cz.cernilovsky.android.rickandmorty.core.AppBuildConfig
import org.koin.core.module.Module
import org.koin.dsl.module

/** App/build info such as the debuggable flag (see [AppBuildConfig]). */
val commonPlatformModule: Module =
    module {
        single {
            val debuggable = (get<Context>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
            AppBuildConfig(isDebug = debuggable)
        }
    }
