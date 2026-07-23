package cz.cernilovsky.android.rickandmorty.characters.ui.detail

import cz.cernilovsky.android.rickandmorty.characters.domain.model.CharacterDetail
import cz.cernilovsky.android.rickandmorty.episode.domain.model.Episode
import cz.cernilovsky.android.rickandmorty.location.domain.model.Location

fun CharacterDetail.toUi(): UiCharacterDetail =
    UiCharacterDetail(
        id = character.id,
        name = character.name,
        image = character.image,
        status = character.status,
        species = character.species,
        type = character.type,
        gender = character.gender,
        originName = character.origin.name,
        origin = origin?.toUi(),
        locationName = character.location.name,
        location = location?.toUi(),
        episodes = episodes.map { it.toUi() },
    )

private fun Location.toUi(): UiLocation =
    UiLocation(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
    )

private fun Episode.toUi(): UiEpisode =
    UiEpisode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
    )
