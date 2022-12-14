package com.example.ricknmorty.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ricknmorty.models.FilterCharacter
import com.example.ricknmorty.models.FilterEpisode
import com.example.ricknmorty.models.FilterLocation
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.models.response.Location
import com.example.ricknmorty.network.CharacterRepository
import com.example.ricknmorty.ui.charactersList.dataSource.CharacterDataSourceFactory
import com.example.ricknmorty.ui.episodes.dataSource.EpisodeListDataSourceFactory
import com.example.ricknmorty.ui.locations.dataSource.LocationListDataSourceFactory

class RickNMortyViewModel : ViewModel() {
    private var repository: CharacterRepository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(40)
        .build()
    private val characterDataSourceFactory = CharacterDataSourceFactory(viewModelScope, repository)
    var characterListLiveData: LiveData<PagedList<CharacterInfo>> =
        LivePagedListBuilder(characterDataSourceFactory, pageListConfig).build()

    private val episodeListDataSourceFactory =
        EpisodeListDataSourceFactory(viewModelScope, repository)
    var episodeListLiveData: LiveData<PagedList<Episode>> =
        LivePagedListBuilder(episodeListDataSourceFactory, pageListConfig).build()

    private val locationListDataSourceFactory =
        LocationListDataSourceFactory(viewModelScope, repository)
    var locationListLiveData: LiveData<PagedList<Location>> =
        LivePagedListBuilder(locationListDataSourceFactory, pageListConfig).build()

    private var _filterCharacter = FilterCharacter()
    private var _filterLocation = FilterLocation()
    private var _filterEpisode = FilterEpisode()

    private val _statusViewStateLiveData = MutableLiveData<List<ChipViewState>>()
    val statusViewStateLiveData: LiveData<List<ChipViewState>>
        get() = _statusViewStateLiveData

    private val _genderViewStateLiveData = MutableLiveData<List<ChipViewState>>()
    val genderViewStateLiveData: LiveData<List<ChipViewState>>
        get() = _genderViewStateLiveData

    fun initialChip() {
        val statusList = ArrayList<ChipViewState>()
        val initialStatusList = listOf<String>("alive", "dead", "unknown")
        val genderList = ArrayList<ChipViewState>()
        val initialGenderList = listOf<String>("male", "female", "genderless", "unknown")

        initialStatusList.forEach {
            statusList.add(
                ChipViewState(
                    value = it,
                    isSelected = false
                )
            )
        }
        _statusViewStateLiveData.postValue(statusList)

        initialGenderList.forEach {
            genderList.add(
                ChipViewState(
                    value = it,
                    isSelected = false
                )
            )
        }
        _genderViewStateLiveData.postValue(genderList)
    }

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

    fun saveFilterCharacterInfo(filterType: CharacterFilterType, value: String) {
        when (filterType) {
            CharacterFilterType.NAME -> _filterCharacter.name = value
            CharacterFilterType.SPECIES -> _filterCharacter.species = value
            CharacterFilterType.TYPE -> _filterCharacter.type = value
            CharacterFilterType.STATUS -> _filterCharacter.status = value
            CharacterFilterType.GENDER -> _filterCharacter.gender = value
        }
        val filterChar = CharacterDataSourceFactory(viewModelScope, repository, _filterCharacter)
        characterListLiveData = LivePagedListBuilder(filterChar, pageListConfig).build()
    }

    fun saveFilterLocation(filterType: LocationFilterType,value: String) {
        when (filterType) {
            LocationFilterType.NAME -> _filterLocation.name = value
            LocationFilterType.TYPE -> _filterLocation.type = value
            LocationFilterType.DIMENSION -> _filterLocation.dimension = value
        }
        val filterLocation = LocationListDataSourceFactory(viewModelScope,repository, _filterLocation)
        locationListLiveData = LivePagedListBuilder(filterLocation, pageListConfig).build()
    }

    fun saveFilterEpisode(filterType: EpisodeFilterType,value: String) {
        when (filterType) {
            EpisodeFilterType.EPISODE -> _filterEpisode.episode = value
            EpisodeFilterType.NAME -> _filterEpisode.name = value
        }
        val filterEpisode = EpisodeListDataSourceFactory(viewModelScope, repository, _filterEpisode)
        episodeListLiveData = LivePagedListBuilder(filterEpisode, pageListConfig).build()
    }

    fun onChipFilterSelected(filterType: CharacterFilterType, status: String) {
        val chipList = ArrayList<ChipViewState>()
        val viewStateLiveData =
            if (filterType == CharacterFilterType.STATUS) _statusViewStateLiveData else _genderViewStateLiveData
        viewStateLiveData.value!!.forEach {
            chipList.add(
                ChipViewState(
                    value = it.value,
                    isSelected = it.value == status
                )
            )
        }
        saveFilterCharacterInfo(filterType, status)
        viewStateLiveData.postValue(chipList)
    }

    data class ChipViewState(
        val value: String,
        val isSelected: Boolean = false
    )
}

enum class CharacterFilterType {
    NAME, STATUS, TYPE, GENDER, SPECIES
}

enum class EpisodeFilterType {
    NAME, EPISODE
}
enum class LocationFilterType {
    NAME, TYPE, DIMENSION
}

