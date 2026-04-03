package com.estebanposada.domain.repository

import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Resource<List<Character>>
    suspend fun getCharacterById(id: String): Resource<Character>
}
