package com.example.ricknmorty.ui.characters

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.GetEpisodeInterface
import com.example.ricknmorty.databinding.ModelCharacterDetailBinding
import com.example.ricknmorty.databinding.ModelCharacterEpisodeBinding
import com.example.ricknmorty.databinding.ModelCharacterHeaderBinding
import com.example.ricknmorty.databinding.ModelCharacterProfileBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.CharacterInfo
import com.squareup.picasso.Picasso

class CharacterDetailEpoxyController(private val characterEpisodeInterface: GetEpisodeInterface): EpoxyController() {

    var characterInfo : CharacterInfo? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if(characterInfo != null) {
            ProfileImageEpoxyModel(characterInfo!!.image).id("image_${characterInfo!!.id}").addTo(this)
            HeaderDetailEpoxyModel(characterInfo!!.name).id("name_${characterInfo!!.id}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.gender,"Gender").id("gender_${characterInfo!!.id}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.species,"Species").id("species_${characterInfo!!.id}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.created,"Created").id("created_${characterInfo!!.id}").addTo(this)
            ProfileDetailEpoxyModel(characterInfo!!.location.name,"Location").id("location_${characterInfo!!.id}").addTo(this)
            if(characterInfo!!.type.isNotEmpty()){
                ProfileDetailEpoxyModel(characterInfo!!.type,"Type").id("type_${characterInfo!!.id}").addTo(this)
            }
            ProfileDetailEpoxyModel(characterInfo!!.status,"Status").id("status_${characterInfo!!.id}").addTo(this)
            CharacterEpisodeEpoxyModel(characterInfo!!.episode,characterEpisodeInterface).id("episode_${characterInfo!!.id}").addTo(this)
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

    data class CharacterEpisodeEpoxyModel(private val characterEpisode: List<String>,private val characterEpisodeInterface: GetEpisodeInterface) : ViewBindingKotlinModel<ModelCharacterEpisodeBinding>(R.layout.model_character_episode) {
        override fun ModelCharacterEpisodeBinding.bind() {
            val characterEpisodeController = CharacterEpisodeController(characterEpisodeInterface)
            characterEpisodeController.characterEpisode = characterEpisode
            episodeList.setController(characterEpisodeController)
        }
    }
}