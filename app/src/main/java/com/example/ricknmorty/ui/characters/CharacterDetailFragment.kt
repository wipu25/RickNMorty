package com.example.ricknmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ricknmorty.R
import com.example.ricknmorty.databinding.FragmentCharacterBinding

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val args: CharacterDetailFragmentArgs by navArgs()

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterDetailEpoxyController = CharacterDetailEpoxyController()
        characterDetailEpoxyController.characterInfo = args.characterInfo
        binding.epoxyCharactersDetail.setController(characterDetailEpoxyController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}