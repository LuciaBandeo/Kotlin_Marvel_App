package com.example.data.service.utils.extension

import com.example.data.service.response.DataResponse
import com.example.domain.entity.Character

fun DataResponse.transformDataResponse(): List<Character> {
    val characterList = mutableListOf<Character>()
    data.results.forEach {
        characterList.add(
            Character(
                it.id,
                it.name,
                it.description,
                it.thumbnail.getURL()
            )
        )
    }
    return characterList
}
