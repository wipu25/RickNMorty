package com.example.ricknmorty.network

import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.AllEpisodes
import com.example.ricknmorty.models.response.AllLocations

class CharacterRepository {
    private val networkLayer: NetworkLayer = NetworkLayer

    suspend fun getCharacterByPage(page: Int): AllCharacters? {
        return networkLayer.apiClient.getCharacterPage(page).body()
    }

    suspend fun getAllLocation(page: Int): AllLocations? {
        return networkLayer.apiClient.getAllLocations(page).body()
    }

    suspend fun getAllEpisode(page: Int): AllEpisodes? {
        return networkLayer.apiClient.getAllEpisodes(page).body()
    }

    suspend fun getCharacterFilter(page: Int, filterCharacter: FilterCharacter): AllCharacters? {
        return networkLayer.apiClient.getFilterCharacter(page, filterCharacter).body()
    }
}