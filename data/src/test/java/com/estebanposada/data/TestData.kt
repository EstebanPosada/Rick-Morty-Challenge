package com.estebanposada.data

import com.estebanposada.data.local.CharacterEntity
import com.estebanposada.data.model.CharacterDto
import com.estebanposada.data.model.CharacterListResponse

val characterEntity = CharacterEntity(
    id = "123",
    name = "name",
    species = "specie",
    gender = "gender",
    type = "type",
    status = "status",
    image = "image"
)

val characterDto = CharacterDto(
    id = "123",
    name = "name",
    status = "stats",
    species = "specie",
    type = "type",
    image = "image",
    gender = "gender"
)

val characterResponse = CharacterListResponse(
    results = List(3) {
        characterDto.copy(id = "$it", name = "name $it")
    }
)
