package com.example.data.mappertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.service.response.CharacterResponse
import com.example.data.service.response.ImageResponse
import com.example.data.service.utils.extension.getURL
import com.example.data.service.utils.extension.transformToCharacter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterResponseToCharacterMapperTest {

    private lateinit var characterResponse: CharacterResponse
    private lateinit var imageResponse: ImageResponse

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        imageResponse = ImageResponse(PATH, EXTENSION)
        characterResponse = CharacterResponse(ID, NAME, DESCRIPTION, imageResponse)
    }

    @Test
    fun `transforming character response to character`() {
        val response = characterResponse.transformToCharacter()

        assertEquals(response.id, characterResponse.id)
        assertEquals(response.name, characterResponse.name)
        assertEquals(response.description, characterResponse.description)
        assertEquals(response.imageURL, characterResponse.thumbnail.getURL())
    }

    companion object {
        const val ID = "1"
        const val NAME = "Juan"
        const val DESCRIPTION = "el amigo de Juan"
        const val PATH = "http://urldeprueba.com/docs/marvelmovies/avengers"
        const val EXTENSION = "jpg"
    }
}
