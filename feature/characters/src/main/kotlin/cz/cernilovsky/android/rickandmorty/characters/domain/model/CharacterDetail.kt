package cz.cernilovsky.android.rickandmorty.characters.domain.model

import cz.cernilovsky.android.rickandmorty.episode.domain.model.Episode
import cz.cernilovsky.android.rickandmorty.location.domain.model.Location

data class CharacterDetail(
    val character: Character,
    val origin: Location?,
    val location: Location?,
    val episodes: List<Episode>,
)
