package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.models.CharacterEpoxyModel
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.response.CharacterInfo

class CharacterEpoxyController : PagedListEpoxyController<CharacterInfo>() {

    var charactersList: List<CharacterInfo>? = null
        set(value) {
            field = value
        }

    override fun buildItemModel(currentPosition: Int, item: CharacterInfo?): EpoxyModel<*> {
        item?.let {
            //Movie Item View Model
            return CharacterEpoxyModel(it).id(it.id)

        } ?: run {
            //Loading View Model
            return LoadingEpoxyModel()
                .id("loading")
        }
    }
}