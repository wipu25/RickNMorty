package com.example.ricknmorty.ui.charactersList

import androidx.paging.DataSource
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.network.CharacterRepository
import com.example.ricknmorty.ui.characters.CharactersDataSource
import kotlinx.coroutines.CoroutineScope

class CharacterDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
): DataSource.Factory<Int, CharacterInfo>() {
    override fun create(): DataSource<Int, CharacterInfo> {
        return CharactersDataSource(coroutineScope, repository)
    }

}