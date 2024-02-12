package com.explore.parakram24.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.explore.parakram24.R
import com.explore.parakram24.viewmodel.SponsorsViewModel

class SponsorsFragment : Fragment() {

    companion object {
        fun newInstance() = SponsorsFragment()
    }

    private val viewModel: SponsorsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sponsors, container, false)
    }
}