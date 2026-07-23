package cz.cernilovsky.android.rickandmorty.location.data

import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.Result
import cz.cernilovsky.android.rickandmorty.location.data.remote.LocationDto

interface LocationDataSource {
    suspend fun getLocations(ids: List<Int>): Result<List<LocationDto>, DataError.Remote>
}
