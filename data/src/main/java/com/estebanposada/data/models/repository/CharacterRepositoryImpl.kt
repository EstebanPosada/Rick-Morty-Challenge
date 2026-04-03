package com.estebanposada.data.models.repository

import com.estebanposada.data.models.local.CharacterDao
import com.estebanposada.data.models.remote.ApiService
import com.estebanposada.data.models.remote.safeApiCall
import com.estebanposada.data.models.toDomain
import com.estebanposada.data.models.toEntity
import com.estebanposada.domain.Resource
import com.estebanposada.domain.models.Character
import com.estebanposada.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: CharacterDao
) :
    CharacterRepository {
    override suspend fun getCharacters(page: Int): Resource<List<Character>> =
        when (val response = safeApiCall { api.getCharacters(page) }) {
            is Resource.Success -> {
                val data = response.data.results.map { it.toEntity() }
                dao.insertAll(data)
                val local = dao.getAllCharacters()
                Resource.Success(local.map { it.toDomain() })
            }

            is Resource.Error -> {
                val local = dao.getAllCharacters()
                if (local.isNotEmpty()) {
                    Resource.Success(local.map { it.toDomain() })
                } else response
            }
        }

    override suspend fun getCharacterById(id: String): Resource<Character> {
        val local = dao.getCharacterById(id)
        if (local != null) {
            return Resource.Success(local.toDomain())
        }
        return when (val response = safeApiCall { api.getCharacterById(id) }) {
            is Resource.Success -> {
                val data = response.data.toEntity()
                dao.insert(data)
                Resource.Success(data.toDomain())
            }

            is Resource.Error -> response
        }
    }
}
