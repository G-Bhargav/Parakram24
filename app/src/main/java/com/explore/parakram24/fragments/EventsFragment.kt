package com.explore.parakram24.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.explore.parakram24.R
import androidx.navigation.findNavController
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.explore.parakram24.adapters.EventData
import com.explore.parakram24.adapters.EventsAdapter
import com.explore.parakram24.databinding.FragmentEventsBinding
import com.explore.parakram24.viewmodel.EventsViewModel

class EventsFragment : Fragment() {

    private var _binding : FragmentEventsBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventsViewModel
    private lateinit var adapter : EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentEventsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[EventsViewModel::class.java]

        binding.rvEvents.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvEvents.setHasFixedSize(true)
        val list = listOf(
            EventData("Cricket"),
            EventData("Badminton"),
            EventData("Football"),
            EventData("Hockey"),
            EventData("Volleyball"),
            EventData("Basketball"),
            EventData("Kabaddi"),
            EventData("Athletics"),
            EventData("Table tennis"),
            EventData("Squash"),
            EventData("Chess"),
            EventData("Tennis"),
            EventData("Power lifting"),
            EventData("Boxing"),
            EventData("Karate"),
            )

        val navController = findNavController()
        adapter = EventsAdapter(list){
            navController.navigate(R.id.eventToindiEvent)

        }

        binding.rvEvents.adapter = adapter

        return binding.root
    }

}