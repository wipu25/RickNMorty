package com.example.ricknmorty.ui.locations

import androidx.paging.DataSource
import com.example.ricknmorty.models.response.Location
import com.example.ricknmorty.network.CharacterRepository
import kotlinx.coroutines.CoroutineScope

class LocationListDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharacterRepository
) : DataSource.Factory<Int, Location>() {
    override fun create(): DataSource<Int, Location> {
        return LocationListDataSource(coroutineScope,repository)
    }
}