package com.example.ricknmorty.ui.charactersList

import androidx.paging.PageKeyedDataSource
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
): PageKeyedDataSource<Int, CharacterInfo>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharacterInfo>
    ) {
        coroutineScope.launch {
            val characterList = repository.getCharacterByPage(page = 1)
            callback.onResult(characterList!!.charactersList, null,getPageIndexFromNext(characterList!!.info.next))
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterInfo>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterInfo>) {
        coroutineScope.launch {
            val characterList = repository.getCharacterByPage(page = params.key)
            callback.onResult(characterList!!.charactersList, params.key + 1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}