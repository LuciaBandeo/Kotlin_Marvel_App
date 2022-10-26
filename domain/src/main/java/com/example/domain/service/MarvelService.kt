package com.example.domain.service

import com.example.domain.entity.Character
import com.example.domain.utils.Result

interface MarvelService {
    fun getCharactersList(): Result<List<Character>>
    fun getCharacterById(characterId: String): Result<Character>
}
