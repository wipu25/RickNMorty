package com.example.ricknmorty.models.epoxy

import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelChipsFilterBinding

data class ChipFilterEpoxyModel(private val text: String): ViewBindingKotlinModel<ModelChipsFilterBinding>(
    R.layout.model_chips_filter) {
    override fun ModelChipsFilterBinding.bind() {
        title.text = text
    }
}