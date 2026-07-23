package cz.cernilovsky.android.rickandmorty.characters.ui.list

import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterLocation
import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterStatus

data class UiCharacter(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val location: CharacterLocation,
    val image: String,
)
