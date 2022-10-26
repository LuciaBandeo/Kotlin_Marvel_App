package com.example.appmarvel.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmarvel.mvvm.model.CharacterFragmentModel
import com.example.domain.entity.Character
import com.example.domain.utils.Constants.CHARACTER_NOT_FOUND
import com.example.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterFragmentViewModel(private val characterFragmentModel: CharacterFragmentModel) :
    ViewModel() {

    private val _characterFragmentState: MutableLiveData<CharacterFragmentData> = MutableLiveData()

    val charactersFragmentState: LiveData<CharacterFragmentData>
        get() = _characterFragmentState

    fun fetchCharacterById(characterId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            characterFragmentModel.getCharacterById(characterId)
        }.let { result ->
            when (result) {
                is Result.Success -> _characterFragmentState.postValue(
                    CharacterFragmentData(
                        characterFragmentState = CharacterFragmentState.SHOW_CHARACTER_DETAILS,
                        data = result.data
                    )
                )
                is Result.Failure -> {
                    if (result.exception.message.equals(CHARACTER_NOT_FOUND)) {
                        _characterFragmentState.postValue(
                            CharacterFragmentData(
                                characterFragmentState = CharacterFragmentState.ERROR_CHARACTER_NOT_FOUND
                            )
                        )
                    } else {
                        _characterFragmentState.postValue(
                            CharacterFragmentData(
                                characterFragmentState = CharacterFragmentState.ERROR_SHOW_CHARACTER
                            )
                        )
                    }
                }
            }
        }
    }

    data class CharacterFragmentData(
        val characterFragmentState: CharacterFragmentState,
        val data: Character? = null
    )

    enum class CharacterFragmentState {
        SHOW_CHARACTER_DETAILS,
        ERROR_CHARACTER_NOT_FOUND,
        ERROR_SHOW_CHARACTER,
    }
}
