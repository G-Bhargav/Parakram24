package com.explore.parakram24.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.explore.parakram24.R
import com.explore.parakram24.adapters.IndividualEventAdapter
import com.explore.parakram24.databinding.FragmentIndividualEventBinding
import com.explore.parakram24.viewmodel.IndividualEventViewModel

class IndividualEventFragment : Fragment() {

    private var _binding: FragmentIndividualEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: IndividualEventViewModel
    private lateinit var adapter : IndividualEventAdapter
    private lateinit var dialog: Dialog
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

        viewModel.gamesData.observe(viewLifecycleOwner) { data ->
            adapter.setData(data)
        }

        viewModel.fetchData()


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragment = "home"
    }

}

data class MatchData(
    val date: String = "",
    val league : String = "",
    val likeA: Int = 0,
    val likeB: Int = 0,
    val score: ScoreData = ScoreData(),
    val teamAImage: String = "",
    val teamAname: String = "",
    val teamBImage: String = "",
    val teamBname: String = "",
    val time : String = "",
    val venue: String = ""
)

data class ScoreData(
    val scoreA: Int = 0,
    val scoreB: Int = 0,
    val wicketsA: Int = 0,
    val wicketsB: Int = 0
)

