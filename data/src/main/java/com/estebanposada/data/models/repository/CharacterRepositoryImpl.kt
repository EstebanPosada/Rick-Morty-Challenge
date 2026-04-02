package com.estebanposada.data.models.repository

import com.estebanposada.data.models.remote.ApiService
import com.estebanposada.data.models.remote.safeApiCall
import com.estebanposada.data.models.toCharacter
import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: ApiService) :
    CharacterRepository {
    override suspend fun getCharacters(): Resource<List<Character>> =
        safeApiCall {
            val response = api.getCharacters()
            response.results.map { it.toCharacter() }
        }

    override suspend fun getCharacterById(id: String): Resource<Character> {
        TODO("Not yet implemented")
    }
}
