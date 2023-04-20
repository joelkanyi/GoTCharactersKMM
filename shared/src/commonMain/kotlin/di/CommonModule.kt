package di

import data.network.ApiService
import data.repository.GoTCharactersRepositoryImpl
import domain.repository.GoTCharactersRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.characters.CharactersViewModel
import presentation.details.CharacterDetailsViewModel
import utils.Constants.BASE_URL

fun commonModule() = module {
    single {
        HttpClient(get()) {
            defaultRequest {
                url {
                    host = BASE_URL
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 3000L
                connectTimeoutMillis = 3000L
                socketTimeoutMillis = 3000L
            }
        }
    }

    /**
     * API Service
     */
    single { ApiService(httpClient = get()) }

    /**
     * Repositories
     */
    single<GoTCharactersRepository> { GoTCharactersRepositoryImpl(apiService = get()) }

    /**
     * ViewModels
     */
    single { CharactersViewModel(goTCharactersRepository = get()) }
    single { CharacterDetailsViewModel(goTCharactersRepository = get()) }
}
