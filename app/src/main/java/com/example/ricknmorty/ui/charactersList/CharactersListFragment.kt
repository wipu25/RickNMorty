package com.example.ricknmorty.ui.charactersList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.FilterType
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentCharactersListBinding
import com.example.ricknmorty.models.response.CharacterInfo


class CharactersListFragment: Fragment(), CharactersListInterface, MenuProvider {

    //use binding for ease of getting widget
    private var _binding : FragmentCharactersListBinding? = null
    private val  binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()
    private val genderFilterEpoxyController = GenderFilterEpoxyController{ it -> updateChipFilter(FilterType.GENDER,it)  }
    private val statusFilterEpoxyController = StatusFilterEpoxyController{ it -> updateChipFilter(FilterType.STATUS,it)  }
    private val characterEpoxyController = CharacterEpoxyController(this,genderFilterEpoxyController,statusFilterEpoxyController)

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
        binding.epoxyCharacters.setController(characterEpoxyController)
        bindCharacterLiveData()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        sharedViewModel.initialChip()
        sharedViewModel.statusViewStateLiveData.observe(viewLifecycleOwner) {
            statusItem -> statusFilterEpoxyController.statusFilter = statusItem
        }
        sharedViewModel.genderViewStateLiveData.observe(viewLifecycleOwner) {
                genderItem -> genderFilterEpoxyController.genderFilter = genderItem
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSelected(characterInfo: CharacterInfo) {
        val directions =
            CharactersListFragmentDirections.actionCharacterListToCharacterDetails(
                characterInfo
            )
        findNavController()
            .navigate(directions)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.icon_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_filter-> {
                characterEpoxyController.updateFilterVisibility()
                characterEpoxyController.requestModelBuild()
                if(characterEpoxyController.isFilterVisible)
                    binding.epoxyCharacters.scrollToPosition(0)
                true
            }
            else -> false
        }
        return false
    }

    override fun updateInputFilter(filterType: FilterType, value: Editable?) {
        characterEpoxyController.submitList(null)
        sharedViewModel.saveFilterCharacterInfo(filterType,value.toString())
        bindCharacterLiveData()
    }

    private fun updateChipFilter(filterType: FilterType,value: String) {
        characterEpoxyController.submitList(null)
        sharedViewModel.onChipFilterSelected(filterType,value)
        bindCharacterLiveData()
    }


    private fun bindCharacterLiveData() {
        sharedViewModel.characterListLiveData.observe(viewLifecycleOwner) {
                characterList -> characterEpoxyController.submitList(characterList)
        }
    }
}