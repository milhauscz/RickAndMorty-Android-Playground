package cz.cernilovsky.android.rickandmorty.episode.data.mapper

import cz.cernilovsky.android.rickandmorty.episode.data.local.EpisodeEntity
import cz.cernilovsky.android.rickandmorty.episode.data.remote.EpisodeDto
import cz.cernilovsky.android.rickandmorty.episode.domain.model.Episode

fun EpisodeDto.toEntity(): EpisodeEntity =
    EpisodeEntity(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        url = url,
        created = created,
    )

fun EpisodeEntity.toDomain(): Episode =
    Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        url = url,
        created = created,
    )
