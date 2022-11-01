package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.models.CharacterEpoxyModel
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.response.CharacterInfo

class CharacterEpoxyController(private val onNavigateItem : () -> Unit) : PagedListEpoxyController<CharacterInfo>() {
    override fun buildItemModel(currentPosition: Int, item: CharacterInfo?): EpoxyModel<*> {
        item?.let {
            //Movie Item View Model
            return CharacterEpoxyModel(it,onNavigateItem).id(it.id)

        } ?: run {
            //Loading View Model
            return LoadingEpoxyModel()
                .id("loading")
        }
    }
}