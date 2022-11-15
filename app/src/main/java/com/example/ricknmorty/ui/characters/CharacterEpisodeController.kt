package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.GetEpisodeInterface
import com.example.ricknmorty.databinding.ModelEpisodeChipsBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel

class CharacterEpisodeController(private val characterEpisodeInterface: GetEpisodeInterface) : EpoxyController() {

    var characterEpisode : List<String> ? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        characterEpisode?.forEach { episode ->
            val splitEpisode = episode.split("/").last().toInt()
            val episodeNum = characterEpisodeInterface.getEpisode(splitEpisode)
            val seasonNum = characterEpisodeInterface.getSeason(splitEpisode)
            EpisodeChipEpoxyModel("SS$seasonNum EP.$episodeNum").id("episode_${characterEpisode!!.indexOf(episode)}").addTo(this)
        }
    }

    data class EpisodeChipEpoxyModel(private val episode: String): ViewBindingKotlinModel<ModelEpisodeChipsBinding>(R.layout.model_episode_chips) {
        override fun ModelEpisodeChipsBinding.bind() {
            episodeTitle.text = episode
        }
    }
}