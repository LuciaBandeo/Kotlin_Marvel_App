package com.example.domain.usecasetest

import com.example.domain.database.MarvelRepository
import com.example.domain.entity.Character
import com.example.domain.service.MarvelService
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.usecase.GetCharactersListUseCaseImpl
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
class GetCharactersListUseCaseTest {

    private val marvelService: MarvelService = mock()
    private val marvelDatabase: MarvelRepository = mock()
    private val listOfCharacters: List<Character> = mock()
    private lateinit var getCharactersListUseCase: GetCharactersListUseCase

    @Before
    fun init() {
        getCharactersListUseCase = GetCharactersListUseCaseImpl(marvelService, marvelDatabase)
    }

    @Test
    fun `when service result returns success`() {
        whenever(marvelService.getCharactersList()).thenReturn(Result.Success(listOfCharacters))
        whenever(marvelDatabase.getCharacters()).thenReturn(Result.Success(listOfCharacters))
        val result = getCharactersListUseCase()

        verify(marvelDatabase).updateCharacters(listOfCharacters)
        verify(marvelDatabase).getCharacters()
        assertEquals(listOfCharacters, (result as Result.Success).data)
    }

    @Test
    fun `when service result returns failure - empty database`() {
        whenever(marvelService.getCharactersList()).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTERS_NOT_FOUND
                )
            )
        )
        whenever(marvelDatabase.getCharacters()).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTERS_NOT_FOUND
                )
            )
        )
        val result = getCharactersListUseCase()

        verify(marvelDatabase).getCharacters()
        assertEquals(CHARACTERS_NOT_FOUND, (result as Result.Failure).exception.message)
    }

    @Test
    fun `when service result returns failure - database has data`() {
        whenever(marvelService.getCharactersList()).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTERS_NOT_FOUND
                )
            )
        )
        whenever(marvelDatabase.getCharacters()).thenReturn(Result.Success(listOfCharacters))
        val result = getCharactersListUseCase()

        verify(marvelDatabase).getCharacters()
        assertEquals(listOfCharacters, (result as Result.Success).data)
    }

    companion object {
        private const val CHARACTERS_NOT_FOUND = "CHARACTERS_NOT_FOUND"
    }
}
