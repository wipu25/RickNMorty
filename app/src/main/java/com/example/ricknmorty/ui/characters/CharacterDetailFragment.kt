package com.example.ricknmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ricknmorty.arch.EpisodeFilterType
import com.example.ricknmorty.arch.EpisodeListInterface
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentCharacterBinding

class CharacterDetailFragment : Fragment(), EpisodeListInterface {

    private var _binding: FragmentCharacterBinding? = null
    private val args: CharacterDetailFragmentArgs by navArgs()
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterDetailEpoxyController = CharacterDetailEpoxyController(this)
        characterDetailEpoxyController.characterInfo = args.characterInfo
        binding.epoxyCharactersDetail.setController(characterDetailEpoxyController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getEpisode(episodeNum: Int): Int? {
        return sharedViewModel.getEpisode(episodeNum)
    }

    override fun updateInputFilter(filterType: EpisodeFilterType, value: String) {
//        TODO("Not yet implemented")
    }
}