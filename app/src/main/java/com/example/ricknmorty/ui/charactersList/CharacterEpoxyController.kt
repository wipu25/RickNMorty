package com.example.ricknmorty.ui.charactersList

import androidx.core.widget.addTextChangedListener
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.CharacterFilterType
import com.example.ricknmorty.arch.CharactersListInterface
import com.example.ricknmorty.databinding.ModelCharacterFilterBinding
import com.example.ricknmorty.databinding.ModelCharacterItemBinding
import com.example.ricknmorty.models.epoxy.LoadingEpoxyModel
import com.example.ricknmorty.models.epoxy.ViewBindingKotlinModel
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.ui.charactersList.filter.GenderFilterEpoxyController
import com.example.ricknmorty.ui.charactersList.filter.StatusFilterEpoxyController
import com.squareup.picasso.Picasso

class CharacterEpoxyController(
    private val charactersListInterface: CharactersListInterface,
    private val genderFilterEpoxyController: GenderFilterEpoxyController,
    private val statusFilterEpoxyController: StatusFilterEpoxyController
) : PagedListEpoxyController<CharacterInfo>() {

    var isFilterVisible = false
        private set

    override fun addModels(models: List<EpoxyModel<*>>) {
        val list = models.toMutableList()
        if (isFilterVisible)
            list.add(
                0,
                CharacterFilterEpoxyModel(
                    charactersListInterface,
                    genderFilterEpoxyController,
                    statusFilterEpoxyController
                ).id("filter")
            )
        super.addModels(list.toList())
    }

    override fun buildItemModel(currentPosition: Int, item: CharacterInfo?): EpoxyModel<*> {
        item?.let {
            return CharacterEpoxyModel(it, charactersListInterface).id(it.id)

        } ?: run {
            //Loading View Model
            return LoadingEpoxyModel()
                .id("loading")
        }
    }

    data class CharacterEpoxyModel(
        val characterInfo: CharacterInfo,
        val charactersListInterface: CharactersListInterface
    ) : ViewBindingKotlinModel<ModelCharacterItemBinding>(
        R.layout.model_character_item
    ) {
        override fun ModelCharacterItemBinding.bind() {
            root.setOnClickListener {
                charactersListInterface.onSelected(characterInfo)
            }
            textName.text = characterInfo.name
            Picasso.get().load(characterInfo.image).into(profileImage)
            when (characterInfo.gender) {
                "Female" -> genderImage.setImageResource(R.drawable.female_icon)
                "Male" -> genderImage.setImageResource(R.drawable.male_icon)
                "Genderless" -> genderImage.setImageResource(R.drawable.genderless_icon)
                "unknown" -> genderImage.setImageResource(R.drawable.unknown_icon)
            }

            when (characterInfo.status) {
                "Alive" -> statusImage.setImageResource(R.drawable.alive_icon)
                "Dead" -> statusImage.setImageResource(R.drawable.dead_icon)
                "unknown" -> statusImage.setImageResource(R.drawable.unknown_icon)
            }
        }
    }

    data class CharacterFilterEpoxyModel(
        val charactersListInterface: CharactersListInterface,
        val genderFilterEpoxyController: GenderFilterEpoxyController,
        val statusFilterEpoxyController: StatusFilterEpoxyController
    ) : ViewBindingKotlinModel<ModelCharacterFilterBinding>(R.layout.model_character_filter) {
        override fun ModelCharacterFilterBinding.bind() {
            editFilterName.addTextChangedListener {
                charactersListInterface.updateInputFilter(CharacterFilterType.NAME, it.toString())
            }
            editFilterSpecies.addTextChangedListener {
                charactersListInterface.updateInputFilter(CharacterFilterType.SPECIES, it.toString())
            }
            editFilterType.addTextChangedListener {
                charactersListInterface.updateInputFilter(CharacterFilterType.TYPE, it.toString())
            }

            statusChip.setController(statusFilterEpoxyController)
            genderChip.setController(genderFilterEpoxyController)
            statusFilterEpoxyController.requestModelBuild()
            genderFilterEpoxyController.requestModelBuild()
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    fun updateFilterVisibility() {
        isFilterVisible = !isFilterVisible
    }
}