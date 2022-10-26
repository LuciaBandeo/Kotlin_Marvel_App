package com.example.data.database

import com.example.domain.utils.Constants.CHARACTER_NOT_FOUND
import com.example.data.service.utils.extension.transformCharacter
import com.example.data.service.utils.extension.transformListCharacterEntity
import com.example.domain.database.MarvelRepository
import com.example.domain.entity.Character
import com.example.domain.utils.Result

class MarvelRepositoryImpl(private val marvelDaO: MarvelDaO) :
    MarvelRepository {

    override fun getCharacters(): Result<List<Character>> = marvelDaO.getCharacters().let {
        if (it.isNotEmpty()) {
            Result.Success(it.transformListCharacterEntity())
        } else {
            Result.Failure(Exception(ERROR_CHARACTERS_NOT_FOUND))
        }
    }

    override fun getCharacterById(characterId: String): Result<Character> =
        marvelDaO.getCharacterById(characterId).let {
            if (it.isNotEmpty()) {
                Result.Success(it.transformListCharacterEntity().first())
            } else {
                Result.Failure(Exception(CHARACTER_NOT_FOUND))
            }
        }

    override fun updateCharacters(charactersList: List<Character>) {
        charactersList.forEach {
            marvelDaO.insertCharacter(it.transformCharacter())
        }
    }

    override fun updateCharacterById(character: Character) {
        marvelDaO.insertCharacter(character.transformCharacter())
    }

    companion object {
        private const val ERROR_CHARACTERS_NOT_FOUND = "ERROR_CHARACTERS_NOT_FOUND"
    }
}
