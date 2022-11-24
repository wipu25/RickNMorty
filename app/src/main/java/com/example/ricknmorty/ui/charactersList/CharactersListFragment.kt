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
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentCharactersListBinding
import com.example.ricknmorty.models.response.CharacterInfo


class CharactersListFragment: Fragment(), CharactersListInterface, MenuProvider {

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
        val genderFilterEpoxyController = GenderFilterEpoxyController()
        val statusFilterEpoxyController = StatusFilterEpoxyController()
        binding.statusChip.setController(statusFilterEpoxyController)
        binding.genderChip.setController(genderFilterEpoxyController)
        statusFilterEpoxyController.requestModelBuild()
        genderFilterEpoxyController.requestModelBuild()

        binding.epoxyCharacters.setController(characterEpoxyController)
        sharedViewModel.characterListLiveData.observe(viewLifecycleOwner) {
                characterList -> characterEpoxyController.submitList(characterList)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.editFilterType.addTextChangedListener{
            Log.d("Filter",it.toString())
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
}