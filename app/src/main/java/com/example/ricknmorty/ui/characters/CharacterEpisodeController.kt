package com.example.ricknmorty.ui.characters

import com.airbnb.epoxy.EpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.GetEpisodeInterface
import com.example.ricknmorty.databinding.ModelChipsBinding
import com.example.ricknmorty.databinding.ModelEpisodeSeasonHeaderBinding
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel

class CharacterEpisodeController(private val characterEpisodeInterface: GetEpisodeInterface) : EpoxyController() {

    private var seasonHeaderDisplay: Int = 0
    var characterEpisode : List<String> ? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        characterEpisode?.forEachIndexed { index, episode ->
            val splitEpisode = episode.split("/").last().toInt()
            val episodeNum = characterEpisodeInterface.getEpisode(splitEpisode)
            val seasonNum = isShownNewSeason(splitEpisode)
            if(seasonNum != null){
                SeasonHeaderEpoxyModel("Season $seasonNum").id("header_$seasonNum").addTo(this)
            }
            EpisodeChipEpoxyModel("EP.$episodeNum").id("episode_${characterEpisode!!.indexOf(episode)}").addTo(this)
        }
    }

    fun isShownNewSeason(episodeNum: Int): Int? {
        when {
            episodeNum in 0..12 && seasonHeaderDisplay < 1 -> {
                seasonHeaderDisplay = 1
                return 1
            }
            episodeNum in 12..21 && seasonHeaderDisplay < 2 -> {
                seasonHeaderDisplay = 2
                return 2
            }
            episodeNum in 22..31 && seasonHeaderDisplay < 3 -> {
                seasonHeaderDisplay = 3
                return 3
            }
            episodeNum in 32..41 && seasonHeaderDisplay < 4 -> {
                seasonHeaderDisplay = 4
                return 4
            }
            episodeNum in 42..51 && seasonHeaderDisplay < 5 -> {
                seasonHeaderDisplay = 5
                return 5
            }
        }
        return null
    }

    data class EpisodeChipEpoxyModel(private val episode: String): ViewBindingKotlinModel<ModelChipsBinding>(R.layout.model_chips) {
        override fun ModelChipsBinding.bind() {
            episodeTitle.text = episode
        }
    }

    data class SeasonHeaderEpoxyModel(private val season: String): ViewBindingKotlinModel<ModelEpisodeSeasonHeaderBinding>(R.layout.model_episode_season_header){
        override fun ModelEpisodeSeasonHeaderBinding.bind() {
            seasonText.text = season
        }
        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}