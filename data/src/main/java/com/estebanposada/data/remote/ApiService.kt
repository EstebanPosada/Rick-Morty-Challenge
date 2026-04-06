package com.estebanposada.data.remote

import com.estebanposada.data.model.CharacterDto
import com.estebanposada.data.model.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 0): CharacterListResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharacterDto
}
