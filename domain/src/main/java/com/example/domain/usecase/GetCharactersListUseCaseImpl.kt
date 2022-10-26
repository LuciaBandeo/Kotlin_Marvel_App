package com.example.domain.usecase

import com.example.domain.database.MarvelRepository
import com.example.domain.entity.Character
import com.example.domain.service.MarvelService
import com.example.domain.utils.Result

interface GetCharactersListUseCase {
    operator fun invoke(): Result<List<Character>>
}

class GetCharactersListUseCaseImpl(
    private val marvelService: MarvelService,
    private val marvelRepository: MarvelRepository
) :
    GetCharactersListUseCase {

    override operator fun invoke(): Result<List<Character>> {
        return when (val serviceResult = marvelService.getCharactersList()) {
            is Result.Success -> {
                marvelRepository.updateCharacters(serviceResult.data)
                marvelRepository.getCharacters()
            }
            is Result.Failure -> marvelRepository.getCharacters()
        }
    }
}
