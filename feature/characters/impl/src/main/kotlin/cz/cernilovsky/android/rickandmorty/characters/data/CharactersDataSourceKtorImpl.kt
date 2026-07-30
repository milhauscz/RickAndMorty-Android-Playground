package cz.cernilovsky.android.rickandmorty.characters.data

import cz.cernilovsky.android.rickandmorty.characters.data.remote.CharactersResponseDto
import cz.cernilovsky.android.rickandmorty.core.domain.DataError
import cz.cernilovsky.android.rickandmorty.core.domain.Result
import cz.cernilovsky.android.rickandmorty.core.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CharactersDataSourceKtorImpl(
    private val httpClient: HttpClient,
) : CharactersDataSource {
    override suspend fun getCharacters(url: String): Result<CharactersResponseDto, DataError.Remote> =
        safeCall {
            httpClient.get(url)
        }
}
