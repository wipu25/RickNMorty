package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.ModelCharacterDetailBinding
import com.example.ricknmorty.databinding.ModelCharacterHeaderBinding
import com.example.ricknmorty.databinding.ModelCharacterProfileBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.CharacterInfo
import com.squareup.picasso.Picasso

class CharacterDetailEpoxyController: EpoxyController() {

    var characterInfo : CharacterInfo? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if(characterInfo != null) {
            HeaderDetailEpoxyModel(characterInfo!!.name).id("name_${characterInfo!!.id}").addTo(this)
            ProfileImageEpoxyModel(characterInfo!!.image).id("image_${characterInfo!!.id}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.gender,"Gender").id("gender_${characterInfo!!.gender}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.species,"Species").id("gender_${characterInfo!!.gender}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.created,"Created").id("gender_${characterInfo!!.gender}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.location.name,"Location").id("gender_${characterInfo!!.gender}").addTo(this)
            if(characterInfo!!.type.isNotEmpty()){
                ProfileDetailEpoxyModel(characterInfo!!.type,"Type").id("gender_${characterInfo!!.gender}").addTo(this)
            }
            ProfileDetailEpoxyModel(characterInfo!!.status,"Status").id("gender_${characterInfo!!.gender}").addTo(this)
        }
    }

    data class ProfileImageEpoxyModel(private val imageURL: String) : ViewBindingKotlinModel<ModelCharacterProfileBinding>(R.layout.model_character_profile) {
        override fun ModelCharacterProfileBinding.bind() {
            Picasso.get().load(imageURL).into(profileImage)
        }
    }

    data class HeaderDetailEpoxyModel(private val name: String) : ViewBindingKotlinModel<ModelCharacterHeaderBinding>(R.layout.model_character_header) {
        override fun ModelCharacterHeaderBinding.bind() {
            profile.text = name
        }
    }

    data class ProfileDetailEpoxyModel(private val detail: String, private val title: String) : ViewBindingKotlinModel<ModelCharacterDetailBinding>(R.layout.model_character_detail) {
        override fun ModelCharacterDetailBinding.bind() {
            textDetail.text = detail
            textTitle.text = title
        }
    }
}