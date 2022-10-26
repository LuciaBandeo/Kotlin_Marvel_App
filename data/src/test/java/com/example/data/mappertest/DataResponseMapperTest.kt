package com.example.data.mappertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.service.response.CharacterResponse
import com.example.data.service.response.DataResponse
import com.example.data.service.response.ImageResponse
import com.example.data.service.response.ResultResponse
import com.example.data.service.utils.extension.getURL
import com.example.data.service.utils.extension.transformDataResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataResponseMapperTest {

    private lateinit var resultResponse: ResultResponse
    private lateinit var dataResponse: DataResponse
    private lateinit var imageResponse: ImageResponse

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        imageResponse = ImageResponse(PATH, EXTENSION)
        resultResponse = ResultResponse(
            mutableListOf(
                CharacterResponse(ID, NAME, DESCRIPTION, imageResponse)
            )
        )
        dataResponse = DataResponse(resultResponse)
    }

    @Test
    fun `transforming data response to character list`() {
        val response = dataResponse.transformDataResponse()

        assertEquals(response[0].id, resultResponse.results[0].id)
        assertEquals(response[0].name, resultResponse.results[0].name)
        assertEquals(response[0].description, resultResponse.results[0].description)
        assertEquals(response[0].imageURL, resultResponse.results[0].thumbnail.getURL())
    }

    companion object {
        const val ID = "1"
        const val NAME = "Juan"
        const val DESCRIPTION = "el amigo de Juan"
        const val PATH = "http://urldeprueba.com/docs/marvelmovies/avengers"
        const val EXTENSION = "jpg"
    }
}
