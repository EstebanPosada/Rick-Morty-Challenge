package com.estebanposada.data

import com.estebanposada.data.local.CharacterEntity
import com.estebanposada.data.model.CharacterDto
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.models.Gender
import com.estebanposada.domain.models.Origin
import com.estebanposada.domain.models.Status

fun String.toGender() = when (lowercase()) {
    "male" -> Gender.MALE
    "female" -> Gender.FEMALE
    "unknown" -> Gender.UNKNOWN
    else -> Gender.OTHER
}

fun String.toStatus() = when (lowercase()) {
    "alive" -> Status.ALIVE
    "dead" -> Status.DEAD
    else -> Status.UNKNOWN
}

fun CharacterDto.toEntity() = CharacterEntity(
    id = id,
    name = name,
    gender = gender,
    species = species,
    type = type,
    status = status,
    image = image,
)

fun CharacterEntity.toDomain() = Character(
    id = id,
    name = name,
    gender = gender.toGender(),
    species = species,
    type = type,
    status = status.toStatus(),
    origin = Origin(name = name, url = name),
    location = Origin(name = name, url = name),
    image = image,
)
