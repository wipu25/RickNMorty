package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.models.CharacterEpoxyModel

class CharacterEpoxyController : EpoxyController() {
    override fun buildModels() {
        CharacterEpoxyModel("Rick").id("rick").addTo(this)
    }
}