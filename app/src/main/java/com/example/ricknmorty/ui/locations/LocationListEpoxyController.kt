package com.example.ricknmorty.ui.locations

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelLocationItemBinding
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.Location

class LocationListEpoxyController : PagedListEpoxyController<Location>() {
    override fun buildItemModel(currentPosition: Int, item: Location?): EpoxyModel<*> {
        item?.let {
            return LocationItemEpoxyModel(item).id("location_${item.id}")
        } ?: run {
            return LoadingEpoxyModel().id("loading")
        }
    }

    data class LocationItemEpoxyModel(private val location: Location) : ViewBindingKotlinModel<ModelLocationItemBinding>(R.layout.model_location_item){
        override fun ModelLocationItemBinding.bind() {
            Log.d("Location", location.name)
            type.text = location.type
            dimension.text = location.dimension
            locationName.text = location.name
        }
    }
}