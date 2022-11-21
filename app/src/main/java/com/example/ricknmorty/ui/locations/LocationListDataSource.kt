package com.example.ricknmorty.ui.locations

import androidx.paging.PageKeyedDataSource
import com.example.ricknmorty.models.response.Location
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LocationListDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
) : PageKeyedDataSource<Int, Location>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Location>
    ) {
        coroutineScope.launch {
            val locationList = repository.getAllLocation(1)
            callback.onResult(locationList?.results ?: emptyList(),null, getPageIndexFromNext(locationList?.info?.next))
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Location>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Location>) {
        coroutineScope.launch {
            val location = repository.getAllLocation(params.key)
            callback.onResult(location?.results ?: emptyList(),params.key + 1)
        }
    }
    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}