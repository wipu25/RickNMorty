package com.example.ricknmorty.ui.charactersList

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.models.epoxy.ChipFilterEpoxyModel

class GenderFilterEpoxyController: EpoxyController() {
    var genderFilter: List<String> = listOf("female","male","genderless","unknown")

    override fun buildModels() {
        genderFilter.forEach {
            ChipFilterEpoxyModel(it).id(it).addTo(this)
        }
    }
}