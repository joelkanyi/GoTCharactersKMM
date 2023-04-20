package presentation.characters

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.GoTCharacter
import domain.repository.GoTCharactersRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.NetworkResult

class CharactersViewModel(
    private val goTCharactersRepository: GoTCharactersRepository,
) : StateScreenModel<CharactersState>(CharactersState.Init) {

    private fun getAllGoTCharacters() {
        coroutineScope.launch {
            mutableState.value = CharactersState.Loading

            goTCharactersRepository.getAllGoTCharacters().collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        mutableState.value =
                            CharactersState.Error(error = result.errorMessage ?: "Unknown error")
                    }

                    is NetworkResult.Success -> {
                        mutableState.value =
                            CharactersState.Result(characters = result.data ?: emptyList())
                    }
                }
            }
        }
    }

    init {
        getAllGoTCharacters()
    }
}

sealed class CharactersState {
    object Init : CharactersState()
    object Loading : CharactersState()
    data class Result(val characters: List<GoTCharacter>) : CharactersState()
    data class Error(val error: String) : CharactersState()
}
