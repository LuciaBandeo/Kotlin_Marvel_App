package com.example.appmarvel.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmarvel.mvvm.model.CharacterListModel
import com.example.domain.entity.Character
import com.example.domain.utils.Constants.CHARACTERS_NOT_FOUND
import com.example.domain.utils.Constants.EMPTY_STRING
import com.example.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersListViewModel(private val model: CharacterListModel) : ViewModel() {

    private var _charactersListState: MutableLiveData<CharactersListData> = MutableLiveData()

    val charactersListState: LiveData<CharactersListData>
        get() = _charactersListState

    fun fetchCharactersList() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            model.getCharactersList()
        }.let { result ->
            when (result) {
                is Result.Success -> _charactersListState.postValue(
                    CharactersListData(
                        charactersState = CharactersListState.FETCH_CHARACTERS,
                        data = result.data
                    )
                )
                is Result.Failure -> {
                    if (result.exception.message.equals(CHARACTERS_NOT_FOUND)) {
                        _charactersListState.postValue(
                            CharactersListData(
                                charactersState = CharactersListState.ERROR_CHARACTERS_NOT_FOUND
                            )
                        )
                    } else {
                        _charactersListState.postValue(
                            CharactersListData(
                                charactersState = CharactersListState.ERROR_OTHER
                            )
                        )
                    }
                }
            }
        }
    }

    fun onCharacterCardPressed(id: String) {
        _charactersListState.value =
            CharactersListData(CharactersListState.FETCH_CHARACTER_DETAILS, id = id)
    }

    data class CharactersListData(
        val charactersState: CharactersListState,
        val data: List<Character> = emptyList(),
        val id: String = EMPTY_STRING
    )

    enum class CharactersListState {
        FETCH_CHARACTERS,
        ERROR_OTHER,
        ERROR_CHARACTERS_NOT_FOUND,
        FETCH_CHARACTER_DETAILS
    }
}
