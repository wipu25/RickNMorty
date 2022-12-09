package com.example.ricknmorty.ui.charactersList.filter

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.models.epoxy.ChipFilterEpoxyModel

class StatusFilterEpoxyController(private val onItemSelected: (String) -> Unit) :
    EpoxyController() {

    var statusFilter: List<RickNMortyViewModel.ChipViewState> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        statusFilter.forEach {
            ChipFilterEpoxyModel(it, onItemSelected).id(it.value).addTo(this)
        }
    }
}