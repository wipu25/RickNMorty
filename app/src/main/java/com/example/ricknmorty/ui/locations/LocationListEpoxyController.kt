package com.example.ricknmorty.ui.locations

import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.LocationFilterType
import com.example.ricknmorty.arch.LocationListInterface
import com.example.ricknmorty.databinding.ModelLocationFilterBinding
import com.example.ricknmorty.databinding.ModelLocationItemBinding
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.Location

class LocationListEpoxyController(private val locationListInterface: LocationListInterface) :
    PagedListEpoxyController<Location>() {

    var isFilterVisible = false
        private set

    override fun addModels(models: List<EpoxyModel<*>>) {
        val list = models.toMutableList()
        if (isFilterVisible)
            list.add(
                0,
                LocationFilterEpoxyModel(
                    locationListInterface
                ).id("filter")
            )
        super.addModels(list)
    }

    override fun buildItemModel(currentPosition: Int, item: Location?): EpoxyModel<*> {
        item?.let {
            return LocationItemEpoxyModel(item).id("location_${item.id}")
        } ?: run {
            return LoadingEpoxyModel().id("loading")
        }
    }

    data class LocationItemEpoxyModel(private val location: Location) :
        ViewBindingKotlinModel<ModelLocationItemBinding>(R.layout.model_location_item) {
        override fun ModelLocationItemBinding.bind() {
            Log.d("Location", location.name)
            type.text = location.type
            dimension.text = location.dimension
            locationName.text = location.name
        }
    }

    data class LocationFilterEpoxyModel(private val locationListInterface: LocationListInterface) :
        ViewBindingKotlinModel<ModelLocationFilterBinding>(R.layout.model_location_filter) {
        override fun ModelLocationFilterBinding.bind() {
            editFilterDimension.addTextChangedListener {
                locationListInterface.updateInputFilter(LocationFilterType.DIMENSION,it.toString())
            }
            editFilterName.addTextChangedListener {
                locationListInterface.updateInputFilter(LocationFilterType.NAME,it.toString())
            }
            editFilterType.addTextChangedListener {
                locationListInterface.updateInputFilter(LocationFilterType.TYPE,it.toString())
            }
        }
    }

    fun updateFilterVisibility() {
        isFilterVisible = !isFilterVisible
    }
}