package com.example.data.mappertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.entity.CharacterEntity
import com.example.data.service.response.CharacterResponse
import com.example.data.service.response.ImageResponse
import com.example.data.service.response.ResultResponse
import com.example.data.service.utils.extension.getURL
import com.example.data.service.utils.extension.transformListCharacterEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CharacterListDataBaseMapperTest {

    private lateinit var resultResponse: ResultResponse
    private lateinit var listOfEntity: List<CharacterEntity>
    private lateinit var imageResponse: ImageResponse

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        imageResponse = ImageResponse(PATH, EXTENSION)
        resultResponse =
            ResultResponse(mutableListOf(CharacterResponse(ID, NAME, DESCRIPTION, imageResponse)))
        listOfEntity = listOf(
            CharacterEntity(
                ID,
                NAME,
                DESCRIPTION,
                imageResponse.getURL()
            )
        )
    }

    @Test
    fun `transforming list of Character Entity into list of Character`() {
        val transformation = listOfEntity.transformListCharacterEntity()

        assertEquals(ID, transformation.first().id)
        assertEquals(NAME, transformation.first().name)
        assertEquals(DESCRIPTION, transformation.first().description)
    }

    companion object {
        const val ID = "1"
        const val NAME = "Juan"
        const val DESCRIPTION = "el amigo de Juan"
        const val PATH = "http://urldeprueba.com/docs/marvelmovies/avengers"
        const val EXTENSION = "jpg"
    }
}
