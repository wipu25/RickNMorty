package com.example.ricknmorty.arch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.network.CharacterRepository
import com.example.ricknmorty.network.NetworkLayer
import com.example.ricknmorty.ui.characters.CharacterDataSourceFactory
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {
    private var repository: CharacterRepository = CharacterRepository()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(40)
        .build()
    private val dataSourceFactory = CharacterDataSourceFactory(viewModelScope, repository)
    val characterListLiveData: LiveData<PagedList<CharacterInfo>> =
        LivePagedListBuilder(dataSourceFactory, pageListConfig).build()
}