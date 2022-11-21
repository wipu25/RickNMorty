package com.example.ricknmorty.ui.characters

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentCharactersListBinding
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.ui.charactersList.CharacterEpoxyController
import com.example.ricknmorty.ui.charactersList.CharactersListInterface


class CharactersListFragment: Fragment(), CharactersListInterface {

    //use binding for ease of getting widget
    private var _binding : FragmentCharactersListBinding? = null
    private val  binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterEpoxyController = CharacterEpoxyController(this)
        binding.epoxyCharacters.setController(characterEpoxyController)
        sharedViewModel.characterListLiveData.observe(viewLifecycleOwner) {
                characterList -> characterEpoxyController.submitList(characterList)
        }

        val menuHost: MenuHost = requireActivity()
        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.icon_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_filter-> {
                        if(binding.filterSearch.visibility == View.GONE){
                            binding.filterSearch.visibility = View.VISIBLE
                        } else {
                            binding.filterSearch.visibility = View.GONE
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSelected(characterInfo: CharacterInfo) {
        val directions = CharactersListFragmentDirections.actionCharacterListToCharacterDetails(characterInfo)
        findNavController()
            .navigate(directions)
    }
}