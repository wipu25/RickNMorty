package com.example.ricknmorty.models

import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelCharacterItemBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel

data class CharacterEpoxyModel(val name: String) : ViewBindingKotlinModel<ModelCharacterItemBinding>(R.layout.model_character_item) {
    override fun ModelCharacterItemBinding.bind() {
        textName.text = name
    }

}