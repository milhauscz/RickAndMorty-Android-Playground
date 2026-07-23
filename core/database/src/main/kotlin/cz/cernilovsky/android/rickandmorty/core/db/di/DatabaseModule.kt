package cz.cernilovsky.android.rickandmorty.core.db.di

import cz.cernilovsky.android.rickandmorty.characters.data.CharactersRoomDataSource
import cz.cernilovsky.android.rickandmorty.core.AppBuildConfig
import cz.cernilovsky.android.rickandmorty.core.db.AppDatabase
import cz.cernilovsky.android.rickandmorty.core.db.DatabaseConfig
import cz.cernilovsky.android.rickandmorty.core.db.getAppDatabase
import cz.cernilovsky.android.rickandmorty.episode.data.EpisodeRoomDataSource
import cz.cernilovsky.android.rickandmorty.location.data.LocationRoomDataSource
import org.koin.dsl.module

val databaseModule =
    module {
        single {
            DatabaseConfig(allowDestructiveMigration = get<AppBuildConfig>().isDebug)
        }
        single<AppDatabase> {
            getAppDatabase(get(), get<DatabaseConfig>().allowDestructiveMigration)
        }
        single<CharactersRoomDataSource> { get<AppDatabase>().charactersDao() }
        single<LocationRoomDataSource> { get<AppDatabase>().locationDao() }
        single<EpisodeRoomDataSource> { get<AppDatabase>().episodeDao() }
    }
