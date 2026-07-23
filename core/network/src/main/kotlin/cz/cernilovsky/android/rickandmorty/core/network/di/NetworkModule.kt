package cz.cernilovsky.android.rickandmorty.core.network.di

import cz.cernilovsky.android.rickandmorty.core.AppBuildConfig
import cz.cernilovsky.android.rickandmorty.core.network.ClearableCacheStorage
import cz.cernilovsky.android.rickandmorty.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule =
    module {
        single { ClearableCacheStorage() }
        single<HttpClient> {
            HttpClientFactory.create(
                engine = Android.create(),
                isDebug = get<AppBuildConfig>().isDebug,
                cacheStorage = get(),
            )
        }
    }
