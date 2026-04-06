package com.estebanposada.domain.repository

import com.estebanposada.domain.AppError
import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character

class FakeCharacterRepository : CharacterRepository {
    var character: Resource<Character> = Resource.Error(AppError.Unknown)
    var characters: Resource<List<Character>> = Resource.Error(AppError.Unknown)
    override suspend fun getCharacters(page: Int): Resource<List<Character>> = characters

    override suspend fun getCharacterById(id: String): Resource<Character> = character
}
