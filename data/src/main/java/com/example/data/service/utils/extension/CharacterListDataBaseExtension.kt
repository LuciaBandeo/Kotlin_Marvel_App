package com.example.data.service.utils.extension

import com.example.data.entity.CharacterEntity
import com.example.domain.entity.Character

fun List<CharacterEntity>.transformListCharacterEntity(): List<Character> {
    val characterList = mutableListOf<Character>()
    forEach {
        characterList.add(
            Character(
                it.id,
                it.name,
                it.description,
                it.imageURL
            )
        )
    }
    return characterList
}
