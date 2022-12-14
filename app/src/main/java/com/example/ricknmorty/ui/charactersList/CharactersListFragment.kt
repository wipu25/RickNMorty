package com.example.ricknmorty.ui.charactersList

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.CharacterFilterType
import com.example.ricknmorty.arch.CharactersListInterface
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentCharactersListBinding
import com.example.ricknmorty.models.response.CharacterInfo
import com.example.ricknmorty.ui.charactersList.filter.GenderFilterEpoxyController
import com.example.ricknmorty.ui.charactersList.filter.StatusFilterEpoxyController


class CharactersListFragment : Fragment(), CharactersListInterface, MenuProvider {

    //use binding for ease of getting widget
    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()
    private val genderFilterEpoxyController =
        GenderFilterEpoxyController { it -> updateChipFilter(CharacterFilterType.GENDER, it) }
    private val statusFilterEpoxyController =
        StatusFilterEpoxyController { it -> updateChipFilter(CharacterFilterType.STATUS, it) }
    private val characterEpoxyController =
        CharacterEpoxyController(this, genderFilterEpoxyController, statusFilterEpoxyController)
    private var scrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
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
        sharedViewModel.statusViewStateLiveData.observe(viewLifecycleOwner) { statusItem ->
            statusFilterEpoxyController.statusFilter = statusItem
        }
        sharedViewModel.genderViewStateLiveData.observe(viewLifecycleOwner) { genderItem ->
            genderFilterEpoxyController.genderFilter = genderItem
        }

        binding.epoxyCharacters.setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
            scrollPosition += p4
            Log.d("scroll", scrollPosition.toString())
            binding.toTopFab.visibility = if(scrollPosition < -10) View.VISIBLE else View.GONE
        }

        binding.toTopFab.setOnClickListener {
            binding.epoxyCharacters.scrollToPosition(0)
            scrollPosition = 0
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
            R.id.action_filter -> {
                characterEpoxyController.updateFilterVisibility()
                characterEpoxyController.requestModelBuild()
                if (characterEpoxyController.isFilterVisible)
                    binding.epoxyCharacters.scrollToPosition(0)
                true
            }
            else -> false
        }
        return false
    }

    override fun updateInputFilter(filterType: CharacterFilterType, value: String) {
        characterEpoxyController.submitList(null)
        sharedViewModel.saveFilterCharacterInfo(filterType, value)
        bindCharacterLiveData()
    }

    private fun updateChipFilter(filterType: CharacterFilterType, value: String) {
        characterEpoxyController.submitList(null)
        sharedViewModel.onChipFilterSelected(filterType, value)
        bindCharacterLiveData()
    }


    private fun bindCharacterLiveData() {
        sharedViewModel.characterListLiveData.observe(viewLifecycleOwner) { characterList ->
            characterEpoxyController.submitList(characterList)
        }
    }
}