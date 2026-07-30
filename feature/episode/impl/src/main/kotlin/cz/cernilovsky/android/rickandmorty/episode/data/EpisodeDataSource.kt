package cz.cernilovsky.android.rickandmorty.episode.data

import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.Result
import cz.cernilovsky.android.rickandmorty.episode.data.remote.EpisodeDto

interface EpisodeDataSource {
    suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeDto>, DataError.Remote>
}
