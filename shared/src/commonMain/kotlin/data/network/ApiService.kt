package data.network

import data.network.dto.GoTCharactersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ApiService(
    private val httpClient: HttpClient,
) {
    suspend fun getAllGoTCharacters(): List<GoTCharactersResponseDto> {
        return httpClient.get(urlString = "/api/v2/Characters").bodyAsText().let { json ->
            Json.decodeFromString(json)
        }
    }

    suspend fun getCharacterById(id: Int): GoTCharactersResponseDto {
        return httpClient.get(urlString = "/api/v2/Characters/$id").bodyAsText().let { json ->
            Json.decodeFromString(json)
        }
    }
}
