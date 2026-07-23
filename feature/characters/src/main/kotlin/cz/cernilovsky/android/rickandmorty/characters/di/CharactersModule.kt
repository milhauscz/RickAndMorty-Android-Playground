package cz.cernilovsky.android.rickandmorty.characters.di

import cz.cernilovsky.android.rickandmorty.characters.data.CharactersDataSource
import cz.cernilovsky.android.rickandmorty.characters.data.CharactersDataSourceKtorImpl
import cz.cernilovsky.android.rickandmorty.characters.data.CharactersRepositoryImpl
import cz.cernilovsky.android.rickandmorty.characters.domain.CharactersRepository
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.GetCharacterDetailUseCase
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.GetCharactersUseCase
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.ObserveCharacterFiltersUseCase
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.ObserveSelectedCharacterIdUseCase
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.SetCharacterFiltersUseCase
import cz.cernilovsky.android.rickandmorty.characters.domain.usecase.SetSelectedCharacterIdUseCase
import cz.cernilovsky.android.rickandmorty.characters.ui.CharactersViewModel
import cz.cernilovsky.android.rickandmorty.characters.ui.detail.CharacterDetailViewModel
import cz.cernilovsky.android.rickandmorty.characters.ui.filters.CharacterFiltersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val charactersModule =
    module {
        factoryOf(::GetCharactersUseCase)
        factoryOf(::GetCharacterDetailUseCase)
        factoryOf(::ObserveCharacterFiltersUseCase)
        factoryOf(::SetCharacterFiltersUseCase)
        factoryOf(::ObserveSelectedCharacterIdUseCase)
        factoryOf(::SetSelectedCharacterIdUseCase)
        // Long-lived scope for the repository's fire-and-forget selection persistence, outliving any
        // single ViewModel. SupervisorJob so one failed write can't cancel the rest.
        single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
        singleOf(::CharactersRepositoryImpl) bind CharactersRepository::class
        singleOf(::CharactersDataSourceKtorImpl) bind CharactersDataSource::class
        viewModelOf(::CharactersViewModel)
        viewModelOf(::CharacterFiltersViewModel)
        viewModel { (id: Int) -> CharacterDetailViewModel(id, get()) }
    }
