package com.example.appmarvel.mvvm.model

import com.example.domain.entity.Character
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.utils.Result

class CharacterListModel(private val getCharacterListUseCase: GetCharactersListUseCase) {
    fun getCharactersList(): Result<List<Character>> = getCharacterListUseCase()
}
