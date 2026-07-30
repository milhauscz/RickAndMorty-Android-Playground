package cz.cernilovsky.android.rickandmorty.characters.data

import cz.cernilovsky.android.rickandmorty.characters.data.remote.CharactersResponseDto
import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.Result

class FakeCharactersDataSource : CharactersDataSource {
    var result: Result<CharactersResponseDto, DataError.Remote> =
        Result.Error(DataError.Remote.UNKNOWN)

    var lastRequestedUrl: String? = null

    override suspend fun getCharacters(url: String): Result<CharactersResponseDto, DataError.Remote> {
        lastRequestedUrl = url
        return result
    }
}
