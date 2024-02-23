package com.explore.parakram24.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.explore.parakram24.R
import com.explore.parakram24.adapters.EditableIndividualEventAdapter
import com.explore.parakram24.adapters.IndividualEventAdapter
import com.explore.parakram24.databinding.FragmentEditableIndividualEventBinding
import com.explore.parakram24.databinding.FragmentIndividualEventBinding
import com.explore.parakram24.viewmodel.EditableIndividualEventViewModel
import com.explore.parakram24.viewmodel.IndividualEventViewModel

class EditableIndividualEventFragment : Fragment(), OnFieldUpdateListener {

    private var _binding: FragmentEditableIndividualEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditableIndividualEventViewModel
    private lateinit var adapter : EditableIndividualEventAdapter
    private lateinit var dialog: Dialog
    private lateinit var swipeLayout : SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditableIndividualEventBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[EditableIndividualEventViewModel::class.java]

        binding.rvItemEditableEvent.layoutManager = LinearLayoutManager(context)
        binding.rvItemEditableEvent.setHasFixedSize(true)
        adapter = EditableIndividualEventAdapter(emptyList(),this)
        binding.rvItemEditableEvent.adapter = adapter
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

        binding.etButtonAdd.setOnClickListener{
            viewModel.addNewGame()
        }


        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        }

        viewModel.etGames.observe(viewLifecycleOwner) { data ->
            data[currentFragment]?.let { adapter.setData(it) }
        }

        viewModel.fetchData()

        swipeLayout = binding.swipeLayoutEditableEvents
        swipeLayout.setOnRefreshListener {
            viewModel.fetchData()
            swipeLayout.isRefreshing = false
        }


        return binding.root
    }


    override fun onUpdateField(fragment : String, cardkey : String , fieldUpdated : String , updatedValue : String) {
        viewModel.update(fragment,cardkey,fieldUpdated,updatedValue)
    }
}


interface OnFieldUpdateListener {
    fun onUpdateField(fragment : String, cardkey : String , fieldUpdated : String , updatedValue : String)
}
