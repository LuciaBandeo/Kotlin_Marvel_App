package com.example.domain.database

import com.example.domain.entity.Character
import com.example.domain.utils.Result

interface MarvelRepository {
    fun getCharacters(): Result<List<Character>>
    fun getCharacterById(id: String): Result<Character>
    fun updateCharacters(charactersList: List<Character>)
    fun updateCharacterById(character: Character)
}
