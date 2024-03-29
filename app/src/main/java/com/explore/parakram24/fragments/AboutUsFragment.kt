package com.explore.parakram24.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.explore.parakram24.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {

    private lateinit var binding : FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsBinding.inflate(inflater)
        return binding.root
    }

}