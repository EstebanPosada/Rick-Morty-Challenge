package com.estebanposada.rickmorty_app

import kotlinx.serialization.Serializable

@Serializable
object CharactersScreen

@Serializable
data class DetailCharacterScreen(val characterId: String)
