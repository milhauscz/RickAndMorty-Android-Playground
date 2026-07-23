package cz.cernilovsky.android.rickandmorty.episode.domain

import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.EmptyResult
import cz.cernilovsky.android.rickandmorty.episode.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun observeByUrls(urls: List<String>): Flow<List<Episode>>

    suspend fun refreshByUrls(urls: List<String>): EmptyResult<DataError.Remote>
}
