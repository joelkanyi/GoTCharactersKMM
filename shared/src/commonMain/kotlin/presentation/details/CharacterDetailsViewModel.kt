package presentation.details

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.model.GoTCharacter
import domain.repository.GoTCharactersRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utils.NetworkResult

class CharacterDetailsViewModel(
    private val goTCharactersRepository: GoTCharactersRepository,
) : StateScreenModel<CharacterDetailsState>(CharacterDetailsState.Init) {

    fun getCharacterDetails(id: Int) {
        coroutineScope.launch {
            mutableState.value = CharacterDetailsState.Loading

            goTCharactersRepository.getCharacterById(id).collectLatest { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        mutableState.value =
                            CharacterDetailsState.Error(
                                error = result.errorMessage ?: "Unknown error",
                            )
                    }

                    is NetworkResult.Success -> {
                        mutableState.value =
                            CharacterDetailsState.Result(character = result.data)
                    }
                }
            }
        }
    }
}

sealed class CharacterDetailsState {
    object Init : CharacterDetailsState()
    object Loading : CharacterDetailsState()
    data class Result(val character: GoTCharacter?) : CharacterDetailsState()
    data class Error(val error: String) : CharacterDetailsState()
}
