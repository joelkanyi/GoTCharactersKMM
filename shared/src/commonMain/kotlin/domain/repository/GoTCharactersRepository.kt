package domain.repository

import domain.model.GoTCharacter
import kotlinx.coroutines.flow.Flow
import utils.NetworkResult

interface GoTCharactersRepository {
    suspend fun getAllGoTCharacters(): Flow<NetworkResult<List<GoTCharacter>>>
    suspend fun getCharacterById(id: Int): Flow<NetworkResult<GoTCharacter>>
}
