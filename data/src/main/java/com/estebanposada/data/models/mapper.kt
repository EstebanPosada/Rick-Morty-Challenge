package com.estebanposada.data.models

import com.estebanposada.data.models.model.CharacterDto
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.models.Gender
import com.estebanposada.domain.models.Origin
import com.estebanposada.domain.models.Status

fun CharacterDto.toCharacter() = Character(
    id = id,
    name = name,
    gender = gender.toGender(),
    species = species,
    type = type,
    status = status.toStatus(),
    origin = Origin(name = name, url = name),
    location = Origin(name = name, url = name),
    image = image,
    url = name
)

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
