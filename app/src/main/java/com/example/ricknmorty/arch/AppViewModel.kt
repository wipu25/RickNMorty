package com.example.ricknmorty.arch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.network.NetworkLayer
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    private var networkLayer: NetworkLayer = NetworkLayer()
    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(40)
        .build()
    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope, repository

    init {
        getCharacter()
    }

}