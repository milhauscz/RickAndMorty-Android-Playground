package cz.cernilovsky.android.rickandmorty.characters.domain.usecase

import cz.cernilovsky.android.rickandmorty.characters.domain.CharactersRepository

class SetSelectedCharacterIdUseCase(
    private val charactersRepository: CharactersRepository,
) {
    suspend operator fun invoke(id: Int?) = charactersRepository.setSelectedCharacterId(id)
}
