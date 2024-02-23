package com.explore.parakram24.fragments

import android.app.Application
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.explore.parakram24.R
import com.explore.parakram24.adapters.IndividualEventAdapter
import com.explore.parakram24.databinding.FragmentIndividualEventBinding
import com.explore.parakram24.viewmodel.IndividualEventViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class IndividualEventFragment : Fragment() {

    private var _binding: FragmentIndividualEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IndividualEventViewModel
    private lateinit var adapter : IndividualEventAdapter
    private lateinit var dialog: Dialog
    private lateinit var swipeLayout : SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualEventBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[IndividualEventViewModel::class.java]
        binding.rvItemEvent.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvItemEvent.setHasFixedSize(true)
        adapter = IndividualEventAdapter(emptyList())
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
            if (showLoading) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.games.observe(viewLifecycleOwner) { data ->
            data[currentFragment]?.let { adapter.setData(it) }
        }

        viewModel.fetchData()

        swipeLayout = binding.swipeLayoutEvents
        swipeLayout.setOnRefreshListener {
            viewModel.fetchData()
            swipeLayout.isRefreshing = false
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragment = "home"
    }

}

data class MatchData(
    val key: String = "",
    val date: String = "Date",
    val league : String = "League",
    val likeA: String = "0",
    val likeB: String = "0",
    val score: ScoreData = ScoreData(),
    val teamAImage: String = "",
    val teamAname: String = "TEAM A",
    val teamBImage: String = "",
    val teamBname: String = "TEAM B",
    val time : String = "Time",
    val venue: String = "Venue"
)

data class ScoreData(
    val scoreA: String = "0",
    val scoreB: String = "0",
    val wicketsA: String = "0",
    val wicketsB: String = "0"
)

class MyFirebase : Application(){
    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }

}

