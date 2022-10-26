package com.example.data.mappertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.service.utils.extension.transformCharacter
import com.example.domain.entity.Character
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterDataBaseMapperTest {

    private lateinit var character: Character

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        character = Character(ID, NAME, DESCRIPTION, URL)
    }

    @Test
    fun `transforming Character to CharacterEntity`() {
        val transformation = character.transformCharacter()

        assertEquals(ID, transformation.id)
        assertEquals(NAME, transformation.name)
        assertEquals(DESCRIPTION, transformation.description)
        assertEquals(URL, transformation.imageURL)
    }

    companion object {
        const val ID = "1"
        const val NAME = "Juan"
        const val DESCRIPTION = "el amigo de Juan"
        const val URL = "http://urldeprueba.com/docs/marvelmovies/avengers.jpg"
    }
}
