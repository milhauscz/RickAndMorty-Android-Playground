package cz.cernilovsky.android.rickandmorty.characters.domain.usecase

import cz.cernilovsky.android.rickandmorty.characters.domain.CharactersRepository
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterFilters
import kotlinx.coroutines.flow.Flow

class ObserveCharacterFiltersUseCase(
    private val charactersRepository: CharactersRepository,
) {
    operator fun invoke(): Flow<CharacterFilters> = charactersRepository.filters
}
