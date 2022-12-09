package com.example.ricknmorty.ui.charactersList

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.models.epoxy.ChipFilterEpoxyModel

class GenderFilterEpoxyController(private val onItemSelected: (String) -> Unit): EpoxyController() {
    var genderFilter: List<RickNMortyViewModel.ChipViewState> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        genderFilter.forEach {
            ChipFilterEpoxyModel(it, onItemSelected).id(it.value).addTo(this)
        }
    }
}