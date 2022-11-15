package com.example.ricknmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ricknmorty.arch.GetEpisodeInterface
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentEpisodeBinding

class EpisodeListFragment : Fragment(), GetEpisodeInterface {
    private var _binding : FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val episodeListEpoxyController = EpisodeListEpoxyController(this)
        binding.epoxyEpisodeList.setController(episodeListEpoxyController)
        sharedViewModel.episodeListLiveData.observe(viewLifecycleOwner) {
            episodeListEpoxyController.submitList(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getEpisode(episodeNum: Int): Int? {
        return sharedViewModel.getEpisode(episodeNum)
    }
}