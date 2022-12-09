package com.example.ricknmorty.ui.charactersList.dataSource

import androidx.paging.DataSource
import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope

class CharacterDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository,
    private val filterCharacter: FilterCharacter? = null
) : DataSource.Factory<Int, CharacterInfo>() {
    override fun create(): DataSource<Int, CharacterInfo> {
        return CharactersDataSource(coroutineScope, repository, filterCharacter)
    }
}