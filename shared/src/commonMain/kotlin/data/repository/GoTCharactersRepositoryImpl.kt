package data.repository

import data.mappers.toDomain
import data.network.ApiService
import domain.model.GoTCharacter
import domain.repository.GoTCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import utils.NetworkResult
import utils.safeApiCall

class GoTCharactersRepositoryImpl(
    private val apiService: ApiService,
) : GoTCharactersRepository {
    override suspend fun getAllGoTCharacters(): Flow<NetworkResult<List<GoTCharacter>>> {
        val response = safeApiCall {
            apiService.getAllGoTCharacters().map { it.toDomain() }
        }
        return flowOf(response)
    }

    override suspend fun getCharacterById(id: Int): Flow<NetworkResult<GoTCharacter>> {
        val response = safeApiCall {
            apiService.getCharacterById(id).toDomain()
        }
        return flowOf(response)
    }
}
