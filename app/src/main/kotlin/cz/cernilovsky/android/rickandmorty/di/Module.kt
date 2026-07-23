package cz.cernilovsky.android.rickandmorty.di

import cz.cernilovsky.android.rickandmorty.characters.di.charactersModule
import cz.cernilovsky.android.rickandmorty.core.db.di.databaseModule
import cz.cernilovsky.android.rickandmorty.core.di.commonPlatformModule
import cz.cernilovsky.android.rickandmorty.core.network.di.networkModule
import cz.cernilovsky.android.rickandmorty.episode.di.episodeModule
import cz.cernilovsky.android.rickandmorty.location.di.locationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            commonPlatformModule,
            networkModule,
            databaseModule,
            episodeModule,
            locationModule,
            charactersModule,
        )
    }
}
