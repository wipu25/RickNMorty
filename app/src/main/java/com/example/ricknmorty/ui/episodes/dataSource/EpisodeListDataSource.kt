package com.example.ricknmorty.ui.episodes.dataSource

import androidx.paging.PageKeyedDataSource
import com.example.ricknmorty.models.FilterEpisode
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class EpisodeListDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository,
    private val filterEpisode: FilterEpisode?
) : PageKeyedDataSource<Int, Episode>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Episode>
    ) {
        coroutineScope.launch {
            val episodeList = if(filterEpisode != null) repository.getEpisodeFilter(1,filterEpisode) else repository.getAllEpisode(1)
            callback.onResult(
                episodeList?.results ?: emptyList(),
                null,
                getPageIndexFromNext(episodeList?.info?.next)
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
        coroutineScope.launch {
            val episodeList = if(filterEpisode != null) repository.getEpisodeFilter(params.key,filterEpisode) else repository.getAllEpisode(page = params.key)
            callback.onResult(
                episodeList?.results ?: emptyList(),
                getPageIndexFromNext(episodeList?.info?.next)
            )
        }
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.substringAfter("?page=")?.substringBefore("&")?.toInt()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Episode>) {
//        TODO("Not yet implemented")
    }
}