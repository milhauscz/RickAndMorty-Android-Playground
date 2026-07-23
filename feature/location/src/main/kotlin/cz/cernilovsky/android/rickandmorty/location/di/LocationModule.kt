package cz.cernilovsky.android.rickandmorty.location.di

import cz.cernilovsky.android.rickandmorty.location.data.LocationDataSource
import cz.cernilovsky.android.rickandmorty.location.data.LocationDataSourceKtorImpl
import cz.cernilovsky.android.rickandmorty.location.data.LocationRepositoryImpl
import cz.cernilovsky.android.rickandmorty.location.domain.LocationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule =
    module {
        singleOf(::LocationRepositoryImpl) bind LocationRepository::class
        singleOf(::LocationDataSourceKtorImpl) bind LocationDataSource::class
    }
