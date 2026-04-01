package com.estebanposada.data.models.remote

import com.estebanposada.data.models.model.CharacterListResponse
import retrofit2.http.GET

interface ApiService {
    @GET()
    suspend fun getCharacters(): CharacterListResponse
}
