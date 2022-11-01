package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.CharacterInfo

class CharacterRepository {
    private val networkLayer: NetworkLayer = NetworkLayer

    suspend fun getCharacter(): CharacterInfo? {
        return networkLayer.apiClient!!.getCharacterId(1).body()
    }

    suspend fun getCharacterByPage(page: Int): AllCharacters {
        return networkLayer.apiClient!!.getCharacterPage(page).body()!!
    }
}