package com.explore.parakram24.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.explore.parakram24.R
import com.explore.parakram24.adapters.EditableIndividualEventAdapter
import com.explore.parakram24.databinding.FragmentEditableIndividualEventBinding
import com.explore.parakram24.viewmodel.EditableIndividualEventViewModel
import kotlinx.coroutines.launch
import com.explore.parakram24.MatchData
import com.explore.parakram24.ScoreData
import com.google.android.material.appbar.AppBarLayout

class EditableIndividualEventFragment : Fragment(), OnFieldUpdateListener {

    private var _binding: FragmentEditableIndividualEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EditableIndividualEventViewModel
    private lateinit var adapter : EditableIndividualEventAdapter
    private lateinit var dialog: Dialog
    private lateinit var swipeLayout : SwipeRefreshLayout
    private val args : EventsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditableIndividualEventBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[EditableIndividualEventViewModel::class.java]

        val appBar = activity?.findViewById<ConstraintLayout>(R.id.appBar)?.findViewById<AppBarLayout>(R.id.layoutAppBar)
        val cardView = appBar?.findViewById<CardView>(R.id.cardView)
        val tvTitle = cardView?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = args.fragment

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

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.fetchData()
        }


        swipeLayout = binding.swipeLayoutEditableEvents
        swipeLayout.setOnRefreshListener {
            viewModel.fetchData()
            swipeLayout.isRefreshing = false
        }


        return binding.root
    }


    override fun onUpdateField(fragment : String, cardKey : String , fieldUpdated : String , updatedValue : String) {
        viewModel.update(fragment,cardKey,fieldUpdated,updatedValue)
    }

    override fun openDialog(matchData: MatchData) {
        val alertDialog = LayoutInflater.from(context).inflate(R.layout.update_card, null)

        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_league).setText(matchData.league)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_team1name).setText(matchData.teamAname)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_team2name).setText(matchData.teamBname)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_date).setText(matchData.date)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_time).setText(matchData.time)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_venue).setText(matchData.venue)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field1).setText(matchData.score.field1)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field2).setText(matchData.score.field2)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field3).setText(matchData.score.field3)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField1).setText(matchData.score.leftField1)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField2).setText(matchData.score.leftField2)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField3).setText(matchData.score.leftField3)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField1).setText(matchData.score.rightField1)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField2).setText(matchData.score.rightField2)
        alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField3).setText(matchData.score.rightField3)

        val builder = AlertDialog.Builder(context)
        builder.setView(alertDialog)
        builder.setTitle("Update Details")

        builder.setPositiveButton("Update") { _, _ ->
            val updatedLeague = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_league).text.toString()
            val updatedTeam1Name = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_team1name).text.toString()
            val updatedTeam2Name = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_team2name).text.toString()
            val updatedDate = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_date).text.toString()
            val updatedTime = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_time).text.toString()
            val updatedVenue = alertDialog.findViewById<AppCompatEditText>(R.id.uc_et_venue).text.toString()
            val updatedField1 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field1).text.toString()
            val updatedField2 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field2).text.toString()
            val updatedField3 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_field3).text.toString()
            val updatedLeftField1 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField1).text.toString()
            val updatedLeftField2 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField2).text.toString()
            val updatedLeftField3 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_leftField3).text.toString()
            val updatedRightField1 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField1).text.toString()
            val updatedRightField2 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField2).text.toString()
            val updatedRightField3 = alertDialog.findViewById<AppCompatEditText>(R.id.uc_tv_rightField3).text.toString()

            val newData = MatchData(
                key= matchData.key,
                league = updatedLeague,
                time = updatedTime,
                date = updatedDate,
                venue = updatedVenue,
                teamAname = updatedTeam1Name,
                teamBname = updatedTeam2Name,
                score = ScoreData(
                    field1 = updatedField1,
                    field2 = updatedField2,
                    field3 = updatedField3,
                    leftField1 = updatedLeftField1,
                    leftField2 = updatedLeftField2,
                    leftField3 = updatedLeftField3,
                    rightField1 = updatedRightField1,
                    rightField2 = updatedRightField2,
                    rightField3 = updatedRightField3
                )
            )

            viewModel.update(currentFragment,newData)
        }

        builder.setNegativeButton("Delete") { _ , _ ->
            viewModel.delete(currentFragment,matchData.key)
        }

        builder.setNeutralButton("cancel"){alertDialog1, _ ->
            alertDialog1.dismiss()
        }

        builder.create().show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


interface OnFieldUpdateListener {
    fun onUpdateField(fragment : String, cardKey : String, fieldUpdated : String, updatedValue : String)
    fun openDialog(matchData : MatchData)
}
