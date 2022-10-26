package com.example.data.mappertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.service.response.ImageResponse
import com.example.data.service.utils.extension.getURL
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageResponseMapperTest {

    private lateinit var imageResponse: ImageResponse

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        imageResponse = ImageResponse(PATH, EXTENSION)
    }

    @Test
    fun `transform thumbnail's path and extension to form the url`() {
        val url = imageResponse.getURL()

        assertEquals(URL, url)
    }

    companion object {
        const val PATH = "http://urldeprueba.com/docs/marvelmovies/avengers"
        const val EXTENSION = "jpg"
        const val URL = "$PATH.$EXTENSION"
    }
}
