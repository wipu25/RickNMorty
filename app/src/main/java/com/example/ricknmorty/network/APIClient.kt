package com.example.ricknmorty.network

import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.AllEpisodes
import com.example.ricknmorty.models.response.AllLocations
import com.example.ricknmorty.models.response.CharacterInfo
import retrofit2.Response
import retrofit2.Retrofit

class APIClient(
    private val apiInterface: APIInterface
) {
    suspend fun getCharacterPage(page: Int): Response<AllCharacters> {
        return apiInterface.getCharacterByPage(page)
    }

    suspend fun getAllLocations(page: Int) : Response<AllLocations> {
        return apiInterface.getAllLocations(page)
    }

    suspend fun getAllEpisodes(page: Int) : Response<AllEpisodes> {
        return apiInterface.getAllEpisodes(page)
    }

    suspend fun getFilterCharacter(page: Int,filterCharacter: FilterCharacter): Response<AllCharacters> {
        return apiInterface.getFilterCharacter(
            page,
            filterCharacter.name,
            filterCharacter.status,
            filterCharacter.species,
            filterCharacter.type,
            filterCharacter.gender
        )
    }
}