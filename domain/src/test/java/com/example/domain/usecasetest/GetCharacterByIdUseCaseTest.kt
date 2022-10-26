package com.example.domain.usecasetest

import com.example.domain.database.MarvelRepository
import com.example.domain.entity.Character
import com.example.domain.service.MarvelService
import com.example.domain.usecase.GetCharacterByIdUseCase
import com.example.domain.usecase.GetCharacterByIdUseCaseImpl
import com.example.domain.utils.Result
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetCharacterByIdUseCaseTest {

    private val marvelService: MarvelService = mock()
    private val marvelRepository: MarvelRepository = mock()
    private lateinit var character: Character
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Before
    fun init() {
        character = Character(ID, NAME, DESCRIPTION, URL)
        getCharacterByIdUseCase = GetCharacterByIdUseCaseImpl(marvelService, marvelRepository)
    }

    @Test
    fun `when service result returns success`() {
        whenever(marvelService.getCharacterById(ID)).thenReturn(Result.Success(character))
        whenever(marvelRepository.getCharacterById(ID)).thenReturn(Result.Success(character))
        val result = getCharacterByIdUseCase(character.id)

        verify(marvelRepository).updateCharacterById(character)
        verify(marvelRepository).getCharacterById(character.id)
        assertEquals(character, (result as Result.Success).data)
    }

    @Test
    fun `when service result returns failure - empty database`() {
        whenever(marvelService.getCharacterById(ID)).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTER_NOT_FOUND
                )
            )
        )
        whenever(marvelRepository.getCharacterById(ID)).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTER_NOT_FOUND
                )
            )
        )
        val result = getCharacterByIdUseCase(character.id)

        verify(marvelRepository).getCharacterById(character.id)
        assertEquals(CHARACTER_NOT_FOUND, (result as Result.Failure).exception.message)
    }

    @Test
    fun `when service result returns failure - database has data`() {
        whenever(marvelService.getCharacterById(INVALID_ID)).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTER_NOT_FOUND
                )
            )
        )
        whenever(marvelRepository.getCharacterById(INVALID_ID)).thenReturn(Result.Success(character))
        val result = getCharacterByIdUseCase(INVALID_ID)

        verify(marvelRepository).getCharacterById(INVALID_ID)
        assertEquals(character, (result as Result.Success).data)
    }

    companion object {
        private const val CHARACTER_NOT_FOUND = "CHARACTER_NOT_FOUND"
        private const val ID = "1017100"
        private const val NAME = "Juan"
        private const val DESCRIPTION = "el amigo de Juan"
        private const val URL = "http://urldeprueba.com/docs/marvelmovies/avengers.jpg"
        private const val INVALID_ID = "240199"
    }
}
