package cz.cernilovsky.android.rickandmorty.characters.ui.list

import cz.cernilovsky.android.rickandmorty.characters.domain.model.Character

fun Character.toUiCharacter() =
    UiCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        location = location,
        image = image,
    )
