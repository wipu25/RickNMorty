package com.example.ricknmorty.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.models.response.Location
import com.example.ricknmorty.network.CharacterRepository
import com.example.ricknmorty.ui.charactersList.CharacterDataSourceFactory
import com.example.ricknmorty.ui.episodes.EpisodeListDataSourceFactory
import com.example.ricknmorty.ui.locations.LocationListDataSource
import com.example.ricknmorty.ui.locations.LocationListDataSourceFactory

class RickNMortyViewModel(): ViewModel() {
    private var repository: CharacterRepository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(40)
        .build()
    private val characterDataSourceFactory = CharacterDataSourceFactory(viewModelScope, repository)
    val characterListLiveData: LiveData<PagedList<CharacterInfo>> =
        LivePagedListBuilder(characterDataSourceFactory, pageListConfig).build()

    private val episodeListDataSourceFactory = EpisodeListDataSourceFactory(viewModelScope, repository)
    val episodeListLiveData: LiveData<PagedList<Episode>> =
        LivePagedListBuilder(episodeListDataSourceFactory, pageListConfig).build()

    private val locationListDataSourceFactory = LocationListDataSourceFactory(viewModelScope, repository)
    val locationListLiveData: LiveData<PagedList<Location>> =
        LivePagedListBuilder(locationListDataSourceFactory, pageListConfig).build()

    fun getEpisode(episodeNum: Int): Int? {
        return when {
            episodeNum <= 11 -> episodeNum
            episodeNum <= 21 -> episodeNum - 11
            episodeNum <= 31 -> episodeNum - 21
            episodeNum <= 41 -> episodeNum - 31
            episodeNum <= 51 -> episodeNum - 41
            episodeNum <= 61 -> episodeNum - 51
            else -> null
        }
    }
}