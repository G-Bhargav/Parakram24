package com.explore.parakram24.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.explore.parakram24.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.explore.parakram24.adapters.EventsAdapter
import com.explore.parakram24.databinding.FragmentEventsBinding
import com.explore.parakram24.viewmodel.EventsViewModel
class EventsFragment : Fragment() {

    private var _binding : FragmentEventsBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventsViewModel
    private lateinit var adapter : EventsAdapter
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentEventsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[EventsViewModel::class.java]

        binding.rvEvents.layoutManager= GridLayoutManager(context, 3)
        binding.rvEvents.setHasFixedSize(true)


        val navController = findNavController()
        adapter = EventsAdapter(emptyList()){
            Log.i("event name",it)
            //For General Users :
            //navController.navigate(R.id.eventToindiEvent)
            //For admin app
            navController.navigate(R.id.eventToEditableEvent)
            currentFragment = it
        }

        binding.rvEvents.adapter = adapter

        dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.loadingcard)
        dialog.setCancelable(false)
        val layoutParams = WindowManager.LayoutParams().apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        dialog.window?.attributes = layoutParams
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary4
                    )
                )
            )
            dialog.window!!.setBackgroundDrawableResource(R.color.transparent)

        }


        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.eventData.observe(viewLifecycleOwner) { data ->
            adapter.setData(data)
        }

        viewModel.fetchData()

        return binding.root
    }

}

var currentFragment = "home"