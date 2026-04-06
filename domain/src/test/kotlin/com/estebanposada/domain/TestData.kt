package com.estebanposada.domain

import com.estebanposada.domain.models.Character
import com.estebanposada.domain.models.Gender
import com.estebanposada.domain.models.Origin
import com.estebanposada.domain.models.Status


val origin = Origin(
    name = "name",
    url = "url"
)

val character = Character(
    id = "123",
    name = "name",
    species = "specie",
    gender = Gender.UNKNOWN,
    type = "type",
    status = Status.UNKNOWN,
    origin = origin,
    location = origin,
    image = "image"
)
