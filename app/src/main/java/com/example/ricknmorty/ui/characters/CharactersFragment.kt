package com.example.ricknmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ricknmorty.databinding.FragmentCharacterBinding


class CharactersFragment: Fragment() {

    //use binding for ease of getting widget
    private var _binding : FragmentCharacterBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterEpoxyController = CharacterEpoxyController()
        binding.epoxyCharacters.setController(characterEpoxyController)
        characterEpoxyController.requestModelBuild()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}