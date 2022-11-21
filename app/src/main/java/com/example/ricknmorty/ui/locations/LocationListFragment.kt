package com.example.ricknmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentLocationsBinding

class LocationListFragment : Fragment() {
    private var _binding : FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: RickNMortyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locationListEpoxyController = LocationListEpoxyController()
        binding.locationEpoxy.setController(locationListEpoxyController)
        sharedViewModel.locationListLiveData.observe(viewLifecycleOwner){
            locationList -> locationListEpoxyController.submitList(locationList)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}