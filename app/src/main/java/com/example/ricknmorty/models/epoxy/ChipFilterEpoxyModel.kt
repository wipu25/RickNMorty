package com.example.ricknmorty.models.epoxy

import android.graphics.Color
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.ModelChipsFilterBinding

data class ChipFilterEpoxyModel(
    private val item: RickNMortyViewModel.ChipViewState,
    private val onItemSelected: (String) -> Unit
) : ViewBindingKotlinModel<ModelChipsFilterBinding>(
    R.layout.model_chips_filter
) {
    override fun ModelChipsFilterBinding.bind() {
        root.setOnClickListener {
            onItemSelected(item.value)
        }
        root.strokeWidth = 0
        if (item.isSelected) {
            root.strokeWidth = 2
            root.strokeColor = Color.BLUE
        }
        title.text = item.value
    }
}