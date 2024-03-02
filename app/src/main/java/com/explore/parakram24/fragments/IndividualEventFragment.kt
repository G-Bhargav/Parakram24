package com.explore.parakram24.fragments

import android.app.Application
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.explore.parakram24.R
import com.explore.parakram24.adapters.IndividualEventAdapter
import com.explore.parakram24.viewmodel.IndividualEventViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.explore.parakram24.MatchData
import com.explore.parakram24.databinding.FragmentIndividualEventBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.google.gson.annotations.SerializedName
import java.util.Date

class IndividualEventFragment : Fragment() {

    private var _binding: FragmentIndividualEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IndividualEventViewModel
    private lateinit var adapter : IndividualEventAdapter
    private lateinit var dialog: Dialog
    private lateinit var swipeLayout : SwipeRefreshLayout
    private val args : EventsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("currentTime in IndiEventFragment on ViewCreated :", Date().toString())
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[IndividualEventViewModel::class.java]
        binding.rvItemEvent.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvItemEvent.setHasFixedSize(true)
        adapter = IndividualEventAdapter(emptyList(),args.fragment)
        binding.rvItemEvent.adapter = adapter

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
            Log.i("currentTime in loading :", Date().toString())
            if (showLoading) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.games.observe(viewLifecycleOwner) { data  ->
            data[currentFragment]?.let { adapter.setData(it) }
        }

        Log.i("currentTime before fetchdata :", Date().toString())
        viewModel.fetchData(args.fragment)

        swipeLayout = binding.swipeLayoutEvents
        swipeLayout.setOnRefreshListener {
            viewModel.fetchData(args.fragment)
            swipeLayout.isRefreshing = false
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragment = "home"
        _binding=null
    }

}


class MyFirebase : Application(){
    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }

}

