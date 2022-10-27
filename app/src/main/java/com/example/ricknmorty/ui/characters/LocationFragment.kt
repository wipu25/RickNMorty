package com.example.ricknmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ricknmorty.databinding.FragmentLocationsBinding

class LocationFragment : Fragment() {
    private var _binding : FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}