package com.estebanposada.domain.models

data class Character(
    val id: String,
    val name: String,
    val species: String,
    val gender: Gender,
    val type: String,
    val status: Status,
    val origin: Origin,
    val location: Origin,
    val image: String,
)



