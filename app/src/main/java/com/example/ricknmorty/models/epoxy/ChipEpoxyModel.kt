package com.example.ricknmorty.models.epoxy

import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelChipsBinding

data class ChipEpoxyModel(private val text: String): ViewBindingKotlinModel<ModelChipsBinding>(
    R.layout.model_chips) {
    override fun ModelChipsBinding.bind() {
        episodeTitle.text = text
    }
}