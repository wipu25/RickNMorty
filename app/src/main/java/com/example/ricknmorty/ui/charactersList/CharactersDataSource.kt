package com.example.ricknmorty.ui.charactersList

import androidx.paging.PageKeyedDataSource
import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository,
    private val filterCharacter: FilterCharacter? = null
): PageKeyedDataSource<Int, CharacterInfo>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharacterInfo>
    ) {
        coroutineScope.launch {
            val characterList = if(filterCharacter == null) repository.getCharacterByPage(page = 1) else repository.getCharacterFilter(page = 1,filterCharacter!!)
            val next = getPageIndexFromNext(characterList?.info?.next)
            callback.onResult(characterList?.charactersList ?: emptyList(), null,next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterInfo>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterInfo>) {
        coroutineScope.launch {
            val characterList = if(filterCharacter == null) repository.getCharacterByPage(page = params.key) else repository.getCharacterFilter(page = params.key,filterCharacter!!)
            callback.onResult(characterList?.charactersList ?: emptyList(), params.key + 1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.substringAfter("?page=")?.substringBefore("&")?.toInt()
    }
}