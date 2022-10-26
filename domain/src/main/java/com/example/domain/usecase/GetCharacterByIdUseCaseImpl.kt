package com.example.domain.usecase

import com.example.domain.database.MarvelRepository
import com.example.domain.entity.Character
import com.example.domain.service.MarvelService
import com.example.domain.utils.Result

interface GetCharacterByIdUseCase {
    operator fun invoke(characterId: String): Result<Character>
}

class GetCharacterByIdUseCaseImpl(
    private val marvelService: MarvelService,
    private val marvelRepository: MarvelRepository
) : GetCharacterByIdUseCase {

    override operator fun invoke(characterId: String): Result<Character> {
        return when (val serviceResult = marvelService.getCharacterById(characterId)) {
            is Result.Success -> {
                marvelRepository.updateCharacterById(serviceResult.data)
                marvelRepository.getCharacterById(serviceResult.data.id)
            }
            is Result.Failure -> marvelRepository.getCharacterById(characterId)
        }
    }
}
