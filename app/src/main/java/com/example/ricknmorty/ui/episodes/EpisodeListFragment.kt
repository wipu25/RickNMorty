package com.example.ricknmorty.ui.episodes

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.EpisodeListInterface
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentEpisodeBinding

class EpisodeListFragment : Fragment(), EpisodeListInterface, MenuProvider {
    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()
    private val episodeListEpoxyController = EpisodeListEpoxyController(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.epoxyEpisodeList.setController(episodeListEpoxyController)
        sharedViewModel.episodeListLiveData.observe(viewLifecycleOwner) {
            episodeListEpoxyController.submitList(it)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.icon_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_filter -> {
                episodeListEpoxyController.updateFilterVisibility()
                episodeListEpoxyController.requestModelBuild()
                if (episodeListEpoxyController.isFilterVisible)
                    binding.epoxyEpisodeList.scrollToPosition(0)
                true
            }
            else -> false
        }
        return false
    }

    override fun getEpisode(episodeNum: Int): Int? {
        return sharedViewModel.getEpisode(episodeNum)
    }
}