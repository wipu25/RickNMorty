package com.example.ricknmorty.ui.episodes.dataSource

import androidx.paging.DataSource
import com.example.ricknmorty.models.FilterEpisode
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope

class EpisodeListDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository,
    private val filterEpisode: FilterEpisode? = null
) : DataSource.Factory<Int, Episode>() {
    override fun create(): DataSource<Int, Episode> {
        return EpisodeListDataSource(coroutineScope, repository,filterEpisode)
    }
}