package com.example.data.service

import com.example.data.service.api.MarvelApi
import com.example.domain.utils.Constants.CHARACTERS_NOT_FOUND
import com.example.domain.utils.Constants.CHARACTER_NOT_FOUND
import com.example.data.service.utils.extension.transformDataResponse
import com.example.data.service.utils.extension.transformToCharacter
import com.example.domain.entity.Character
import com.example.domain.service.MarvelService
import com.example.domain.utils.Result

class MarvelServiceImpl(private val api: ServiceGenerator) : MarvelService {

    override fun getCharactersList(): Result<List<Character>> {
        try {
            val callResponse = api.createService(MarvelApi::class.java).getCharactersList()
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.let {
                    return Result.Success(it.transformDataResponse())
                }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
        return Result.Failure(Exception(CHARACTERS_NOT_FOUND))
    }

    override fun getCharacterById(characterId: String): Result<Character> {
        try {
            val callResponse =
                api.createService(MarvelApi::class.java).getCharacterById(characterId)
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.let { return Result.Success(it.transformToCharacter()) }
        } catch (e: Exception) {
            return Result.Failure(e)
        }
        return Result.Failure(Exception(CHARACTER_NOT_FOUND))
    }
}
