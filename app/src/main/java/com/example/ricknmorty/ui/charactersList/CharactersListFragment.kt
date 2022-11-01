package com.example.ricknmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.CharacterViewModel
import com.example.ricknmorty.databinding.FragmentCharactersListBinding


class CharactersListFragment: Fragment() {

    //use binding for ease of getting widget
    private var _binding : FragmentCharactersListBinding? = null
    private val  binding get() = _binding!!
    private val sharedViewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterEpoxyController = CharacterEpoxyController { navigateToCharacterDetails() }
        binding.epoxyCharacters.setController(characterEpoxyController)
        sharedViewModel.characterListLiveData.observe(viewLifecycleOwner) {
                characterList -> characterEpoxyController.submitList(characterList)
        }
    }

    private fun navigateToCharacterDetails() {
        findNavController().navigate(R.id.action_character_list_to_character_details)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}