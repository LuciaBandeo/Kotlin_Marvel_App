package com.example.appmarvel.mvvm.model

import com.example.domain.entity.Character
import com.example.domain.usecase.GetCharacterByIdUseCase
import com.example.domain.utils.Result

class CharacterFragmentModel(private val getCharacterByIdUseCase: GetCharacterByIdUseCase) {
    fun getCharacterById(characterId: String): Result<Character> =
        getCharacterByIdUseCase(characterId)
}
