package com.example.ricknmorty.ui.episodes

import androidx.paging.PageKeyedDataSource
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EpisodeListDataSource (
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
): PageKeyedDataSource<Int, Episode>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Episode>
    ) {
        coroutineScope.launch {
            val episodeList = repository.getAllEpisode(1)
            callback.onResult(episodeList.results,null,getPageIndexFromNext(episodeList.info.next))
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        coroutineScope.launch {
            val episodeList = repository.getAllEpisode(page = params.key)
            callback.onResult(episodeList.results,getPageIndexFromNext(episodeList.info.next))
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
//        TODO("Not yet implemented")
    }
}