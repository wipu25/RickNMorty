package com.example.ricknmorty.ui.episodes

import androidx.core.widget.addTextChangedListener
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.EpisodeListInterface
import com.example.ricknmorty.databinding.ModelEpisodeFilterBinding
import com.example.ricknmorty.databinding.ModelEpisodeSeasonHeaderBinding
import com.example.ricknmorty.models.epoxy.ChipEpoxyModel
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.Episode

class EpisodeListEpoxyController(private val episodeListInterface: EpisodeListInterface) :
    PagedListEpoxyController<Episode>() {

    var isFilterVisible = false
        private set

    override fun addModels(models: List<EpoxyModel<*>>) {
        var list = mutableListOf<EpoxyModel<*>>()
        if (isFilterVisible)
            list.add(
                0,
                EpisodeFilterEpoxyModel(
                    episodeListInterface
                ).id("filter")
            )
        models.forEachIndexed { index, epoxyModel ->
            if (getSeason(index) != null) {
                list.add(SeasonHeaderEpoxyModel("Season ${getSeason(index)}").id("ss_$index"))
            }
            list.add(epoxyModel)
        }
        super.addModels(list)
    }

    private fun getSeason(episodeNum: Int): Int? {
        return when (episodeNum) {
            0 -> 1
            10 -> 2
            21 -> 3
            31 -> 4
            41 -> 5
            51 -> 6
            else -> null
        }
    }

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        item?.let {
            return ChipEpoxyModel("EP${episodeListInterface.getEpisode(it.id.toInt())} ${item.name}").id(
                it.id
            )
        } ?: run {
            return LoadingEpoxyModel().id("loading")
        }
    }

    data class SeasonHeaderEpoxyModel(private val season: String) :
        ViewBindingKotlinModel<ModelEpisodeSeasonHeaderBinding>(R.layout.model_episode_season_header) {
        override fun ModelEpisodeSeasonHeaderBinding.bind() {
            seasonText.text = season
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class EpisodeFilterEpoxyModel(private val episodeListInterface: EpisodeListInterface) :
        ViewBindingKotlinModel<ModelEpisodeFilterBinding>(R.layout.model_episode_filter) {
        override fun ModelEpisodeFilterBinding.bind() {
            editFilterName.addTextChangedListener {
            }
            editFilterEpisode.addTextChangedListener {
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    fun updateFilterVisibility() {
        isFilterVisible = !isFilterVisible
    }
}