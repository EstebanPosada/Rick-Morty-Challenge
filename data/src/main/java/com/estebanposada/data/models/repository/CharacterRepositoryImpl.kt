package com.estebanposada.data.models.repository

import com.estebanposada.data.models.remote.ApiService
import com.estebanposada.data.models.toCharacter
import com.estebanposada.domain.Error
import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: ApiService) :
    CharacterRepository {
    override suspend fun getCharacters(): Resource<List<Character>> = try {
        val response = api.getCharacters()
        Resource.Success(response.results.map { it.toCharacter() })
    } catch (e: Exception) {
        Resource.Error(error = Error.UnknownError)
    }

    override suspend fun getCharacterById(id: String): Resource<Character> {
        TODO("Not yet implemented")
    }
}
