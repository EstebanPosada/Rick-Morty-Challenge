package com.estebanposada.domain.models

data class Character(
    val id: String,
    val name: String,
    val species: String,
    val type: String?=null,
    val status: Status,
    val origin: Origin,
    val location: Origin,
    val image: String,
    val url: String
)



