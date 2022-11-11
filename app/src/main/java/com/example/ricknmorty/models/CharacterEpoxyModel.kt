package com.example.ricknmorty.models

import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelCharacterItemBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.ui.charactersList.CharactersListInterface
import com.squareup.picasso.Picasso

data class CharacterEpoxyModel(val characterInfo: CharacterInfo,val charactersListInterface: CharactersListInterface) : ViewBindingKotlinModel<ModelCharacterItemBinding>(R.layout.model_character_item) {
    override fun ModelCharacterItemBinding.bind() {
        root.setOnClickListener{
            charactersListInterface.onSelected(characterInfo)
        }
        textName.text = characterInfo.name
        textStatus.text = characterInfo.status
        Picasso.get().load(characterInfo.image).into(profileImage)
        when(characterInfo.gender) {
            "Female" -> genderImage.setImageResource(R.drawable.female_icon)
            "Male" -> genderImage.setImageResource(R.drawable.male_icon)
            "Genderless" -> genderImage.setImageResource(R.drawable.genderless_icon)
            "unknown" -> genderImage.setImageResource(R.drawable.unknown_icon)
        }

        when(characterInfo.status){
            "Alive" -> statusImage.setImageResource(R.drawable.alive_icon)
            "Dead" -> statusImage.setImageResource(R.drawable.dead_icon)
            "unknown" -> statusImage.setImageResource(R.drawable.unknown_icon)
        }
    }

}