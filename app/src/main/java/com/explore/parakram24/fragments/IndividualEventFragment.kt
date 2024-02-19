package com.explore.parakram24.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.explore.parakram24.R
import com.explore.parakram24.databinding.FragmentEventsBinding
import com.explore.parakram24.databinding.FragmentIndividualEventBinding

class IndividualEventFragment : Fragment() {

    private var _binding : FragmentIndividualEventBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentIndividualEventBinding.inflate(layoutInflater)



        return binding.root
    }

}