package com.example.data.service.utils.extension

import com.example.data.entity.CharacterEntity
import com.example.domain.entity.Character

fun Character.transformCharacter(): CharacterEntity {
    return CharacterEntity(
        this.id,
        this.name,
        this.description,
        this.imageURL
    )
}
