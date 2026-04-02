package com.estebanposada.data.models.remote

import com.estebanposada.data.models.model.CharacterDto
import com.estebanposada.data.models.model.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterListResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharacterDto
}
