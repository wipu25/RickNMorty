package com.example.ricknmorty.ui.locations

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.ricknmorty.R
import com.example.ricknmorty.arch.RickNMortyViewModel
import com.example.ricknmorty.databinding.FragmentLocationsBinding

class LocationListFragment : Fragment(), MenuProvider {
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
            R.id.action_filter-> {
                if(binding.filterLocation.visibility == View.GONE){
                    binding.filterLocation.visibility = View.VISIBLE
                } else {
                    binding.filterLocation.visibility = View.GONE
                }
                true
            }
            else -> false
        }
    }
}