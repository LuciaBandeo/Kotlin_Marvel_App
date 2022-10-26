package com.example.data.service.utils.extension

import com.example.data.service.response.CharacterResponse
import com.example.domain.entity.Character

fun CharacterResponse.transformToCharacter(): Character {
    return Character(
        this.id,
        this.name,
        this.description,
        this.thumbnail.getURL()
    )
}
