package cz.cernilovsky.android.rickandmorty.episode.di

import cz.cernilovsky.android.rickandmorty.episode.data.EpisodeDataSource
import cz.cernilovsky.android.rickandmorty.episode.data.EpisodeDataSourceKtorImpl
import cz.cernilovsky.android.rickandmorty.episode.data.EpisodeRepositoryImpl
import cz.cernilovsky.android.rickandmorty.episode.domain.EpisodeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val episodeModule =
    module {
        singleOf(::EpisodeRepositoryImpl) bind EpisodeRepository::class
        singleOf(::EpisodeDataSourceKtorImpl) bind EpisodeDataSource::class
    }
