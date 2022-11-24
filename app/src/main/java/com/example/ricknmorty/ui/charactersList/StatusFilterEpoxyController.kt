package com.example.ricknmorty.ui.charactersList

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.models.epoxy.ChipFilterEpoxyModel

class StatusFilterEpoxyController: EpoxyController() {

    var statusFilter: List<String> = listOf("alive","dead","unknown")

    override fun buildModels() {
        statusFilter.forEach {
            ChipFilterEpoxyModel(it).id(it).addTo(this)
        }
    }
}