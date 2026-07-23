package cz.cernilovsky.android.rickandmorty.characters.data

import cz.cernilovsky.android.rickandmorty.characters.data.remote.CharactersResponseDto
import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.Result

interface CharactersDataSource {
    suspend fun getCharacters(url: String): Result<CharactersResponseDto, DataError.Remote>
}
