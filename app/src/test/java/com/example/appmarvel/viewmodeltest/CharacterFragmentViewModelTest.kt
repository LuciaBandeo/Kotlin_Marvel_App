package com.example.appmarvel.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appmarvel.mvvm.model.CharacterFragmentModel
import com.example.appmarvel.mvvm.viewmodel.CharacterFragmentViewModel
import com.example.appmarvel.testObserver
import com.example.domain.entity.Character
import com.example.domain.usecase.GetCharacterByIdUseCase
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
class CharacterFragmentViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var characterFragmentModel: CharacterFragmentModel
    private lateinit var characterFragmentViewModel: CharacterFragmentViewModel
    private lateinit var character: Character
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase = mock()
    private val exception: Exception = mock()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)
        characterFragmentModel = CharacterFragmentModel(getCharacterByIdUseCase)
        characterFragmentViewModel = CharacterFragmentViewModel(characterFragmentModel)
        character = Character(ID, NAME, DESCRIPTION, URL)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchCharactersById returns success result`() {
        val characterLiveData = characterFragmentViewModel.charactersFragmentState.testObserver()
        whenever(getCharacterByIdUseCase(ID)).thenReturn(Result.Success(character))

        runBlocking { characterFragmentViewModel.fetchCharacterById(character.id).join() }
        assertEquals(
            CharacterFragmentViewModel.CharacterFragmentState.SHOW_CHARACTER_DETAILS,
            characterLiveData.observedValues[0]?.characterFragmentState
        )
    }

    @Test
    fun `when fetchCharactersById returns failure -notfound-  result`() {
        val characterLiveData = characterFragmentViewModel.charactersFragmentState.testObserver()
        whenever(getCharacterByIdUseCase(INVALID_ID)).thenReturn(
            Result.Failure(
                Exception(
                    CHARACTER_NOT_FOUND
                )
            )
        )

        runBlocking { characterFragmentViewModel.fetchCharacterById(INVALID_ID).join() }
        assertEquals(
            CharacterFragmentViewModel.CharacterFragmentState.ERROR_CHARACTER_NOT_FOUND,
            characterLiveData.observedValues[0]?.characterFragmentState
        )
    }

    @Test
    fun `when fetchCharacters returns failure -other-  result`() {
        val characterLiveData = characterFragmentViewModel.charactersFragmentState.testObserver()
        whenever(getCharacterByIdUseCase(ID)).thenReturn(Result.Failure(exception))

        runBlocking { characterFragmentViewModel.fetchCharacterById(character.id).join() }
        assertEquals(
            CharacterFragmentViewModel.CharacterFragmentState.ERROR_SHOW_CHARACTER,
            characterLiveData.observedValues[0]?.characterFragmentState
        )
    }

    companion object {
        private const val ID = "1017100"
        private const val NAME = "Juan"
        private const val DESCRIPTION = "el amigo de Juan"
        private const val URL = "http://urldeprueba.com/docs/marvelmovies/avengers.jpg"
        private const val INVALID_ID = "240199"
        private const val CHARACTER_NOT_FOUND = "CHARACTER_NOT_FOUND"
    }
}
