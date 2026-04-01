package com.estebanposada.data.models.repository

import com.estebanposada.data.models.remote.ApiService
import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository

class CharacterRepositoryImpl(private val api: ApiService) : CharacterRepository {
    override suspend fun getCharacters(): Resource<List<Character>> {
        TODO("Not ")//= api.getCharacters()
    }

    override suspend fun getCharacterById(id: String): Resource<Character> {
        TODO("Not yet implemented")
    }
}
