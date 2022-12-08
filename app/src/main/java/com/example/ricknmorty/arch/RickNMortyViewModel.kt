package com.example.ricknmorty.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.models.response.Location
import com.example.ricknmorty.network.CharacterRepository
import com.example.ricknmorty.ui.charactersList.CharacterDataSourceFactory
import com.example.ricknmorty.ui.episodes.EpisodeListDataSourceFactory
import com.example.ricknmorty.ui.locations.LocationListDataSource
import com.example.ricknmorty.ui.locations.LocationListDataSourceFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RickNMortyViewModel: ViewModel() {
    private var repository: CharacterRepository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(40)
        .build()
    private val characterDataSourceFactory = CharacterDataSourceFactory(viewModelScope, repository)
    var characterListLiveData: LiveData<PagedList<CharacterInfo>> =
        LivePagedListBuilder(characterDataSourceFactory, pageListConfig).build()

    private val episodeListDataSourceFactory = EpisodeListDataSourceFactory(viewModelScope, repository)
    val episodeListLiveData: LiveData<PagedList<Episode>> =
        LivePagedListBuilder(episodeListDataSourceFactory, pageListConfig).build()

    private val locationListDataSourceFactory = LocationListDataSourceFactory(viewModelScope, repository)
    val locationListLiveData: LiveData<PagedList<Location>> =
        LivePagedListBuilder(locationListDataSourceFactory, pageListConfig).build()

    private var _filterCharacter = FilterCharacter()

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

    fun saveFilterCharacterInfo(filterType: FilterType,value: String){
        when(filterType) {
            FilterType.NAME -> _filterCharacter.name = value
            FilterType.SPECIES -> _filterCharacter.species = value
            FilterType.TYPE -> _filterCharacter.type = value
            FilterType.STATUS -> _filterCharacter.status = value
            FilterType.GENDER -> _filterCharacter.gender = value
        }
        val filterChar = CharacterDataSourceFactory(viewModelScope, repository, _filterCharacter)
        characterListLiveData = LivePagedListBuilder(filterChar, pageListConfig).build()
    }
}

enum class FilterType {
    NAME,STATUS,TYPE,GENDER,SPECIES
}