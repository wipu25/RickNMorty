package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.CharacterInfo
import retrofit2.Response
import retrofit2.Retrofit

class APIClient(
    private val apiInterface: APIInterface
) {
    suspend fun getCharacterPage(page: Int): Response<AllCharacters> {
        return apiInterface.getCharacterByPage(page)
    }

    suspend fun getCharacterId(id: Int): Response<CharacterInfo> {
        return apiInterface.getCharacter()
    }
}