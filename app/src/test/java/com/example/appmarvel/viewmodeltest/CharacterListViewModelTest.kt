package com.example.appmarvel.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appmarvel.mvvm.model.CharacterListModel
import com.example.appmarvel.mvvm.viewmodel.CharactersListViewModel
import com.example.appmarvel.testObserver
import com.example.domain.entity.Character
import com.example.domain.usecase.GetCharactersListUseCase
import com.example.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {

    private lateinit var characterListViewModel: CharactersListViewModel
    private lateinit var characterListModel: CharacterListModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val getCharactersListUseCase: GetCharactersListUseCase = mock()
    private val charactersList: List<Character> = mock()
    private val exception: Exception = mock()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)
        characterListModel = CharacterListModel(getCharactersListUseCase)
        characterListViewModel = CharactersListViewModel(characterListModel)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchCharacters returns success result`() {
        val charactersLiveData = characterListViewModel.charactersListState.testObserver()
        whenever(getCharactersListUseCase()).thenReturn(Result.Success(charactersList))

        runBlocking { characterListViewModel.fetchCharactersList().join() }
        assertEquals(
            CharactersListViewModel.CharactersListState.FETCH_CHARACTERS,
            charactersLiveData.observedValues[0]?.charactersState
        )
    }

    @Test
    fun `when fetchCharacters returns failure -notfound-  result`() {
        val charactersLiveData = characterListViewModel.charactersListState.testObserver()
        whenever(getCharactersListUseCase()).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTERS_NOT_FOUND
                )
            )
        )

        runBlocking { characterListViewModel.fetchCharactersList().join() }
        assertEquals(
            CharactersListViewModel.CharactersListState.ERROR_CHARACTERS_NOT_FOUND,
            charactersLiveData.observedValues[0]?.charactersState
        )
    }

    @Test
    fun `when fetchCharacters returns failure -other-  result`() {
        val charactersLiveData = characterListViewModel.charactersListState.testObserver()
        whenever(getCharactersListUseCase()).thenReturn(Result.Failure(exception))

        runBlocking { characterListViewModel.fetchCharactersList().join() }
        assertEquals(
            CharactersListViewModel.CharactersListState.ERROR_OTHER,
            charactersLiveData.observedValues[0]?.charactersState
        )
    }

    @Test
    fun `when onCharacterCardPressed`() {
        characterListViewModel.onCharacterCardPressed(ID)
        assertEquals(ID, characterListViewModel.charactersListState.value?.id)
    }

    companion object {
        private const val CHARACTERS_NOT_FOUND = "CHARACTERS_NOT_FOUND"
        private const val ID = "1017100"
    }
}
