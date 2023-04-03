package com.jhoncasique.rickandmorty.data

import com.jhoncasique.rickandmorty.domain.model.CharacterResponse
import com.jhoncasique.rickandmorty.domain.model.Character
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page")
        page: Int
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id")
        id: Int
    ): Character
}