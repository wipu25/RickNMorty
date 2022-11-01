package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.models.response.CharacterInfo

class CharacterDetailEpoxyController: EpoxyController() {

    var characterInfo : CharacterInfo? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
    }
}