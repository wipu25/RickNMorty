package com.example.ricknmorty.ui.episodes

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.GetEpisodeInterface
import com.example.ricknmorty.databinding.ModelEpisodeChipsBinding
import com.example.ricknmorty.databinding.ModelEpisodeSeasonHeaderBinding
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.Episode
import com.example.ricknmorty.ui.characters.CharacterEpisodeController

class EpisodeListEpoxyController(private val episodeListInterface: GetEpisodeInterface): PagedListEpoxyController<Episode>() {

    override fun addModels(models: List<EpoxyModel<*>>) {
        var list = mutableListOf<EpoxyModel<*>>()
        models.forEachIndexed { index, epoxyModel ->
            if(episodeListInterface.getSeason(index) != null){
                list.add(SeasonHeaderEpoxyModel("Season ${episodeListInterface.getSeason(index)}").id("ss_$index"))
            }
            list.add(epoxyModel)
        }
        super.addModels(list)
    }

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        item?.let {
            return EpisodeChipEpoxyModel("EP${episodeListInterface.getEpisode(it.id.toInt())} ${item.name}").id(it.id)
        } ?: run {
            return LoadingEpoxyModel().id("loading")
        }
    }

    data class EpisodeChipEpoxyModel(private val name: String): ViewBindingKotlinModel<ModelEpisodeChipsBinding>(R.layout.model_episode_chips) {
        override fun ModelEpisodeChipsBinding.bind() {
            episodeTitle.text = name
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