package com.explore.parakram24.adapters

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.explore.parakram24.R
import com.explore.parakram24.fragments.MatchData
import com.explore.parakram24.fragments.OnFieldUpdateListener
import com.explore.parakram24.fragments.currentFragment
import kotlin.math.max
import kotlin.math.min

class EditableIndividualEventAdapter(private var etGameslist: List<MatchData>,private val listener : OnFieldUpdateListener): RecyclerView.Adapter<EditableIndividualEventAdapter.ViewHolder>()  {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leagueTextView: EditText = view.findViewById(R.id.et_league)
        val team1Image: ImageView = view.findViewById(R.id.et_team1Image)
        val team1Name: EditText = view.findViewById(R.id.et_team1name)
        val buttonFav1: CheckBox = view.findViewById(R.id.et_button_fav1)
        val team2Image: ImageView = view.findViewById(R.id.et_team2Image)
        val team2Name: EditText = view.findViewById(R.id.et_team2name)
        val buttonFav2: CheckBox = view.findViewById(R.id.et_button_fav2)
        val dateTextView: EditText = view.findViewById(R.id.et_date)
        val timeTextView: EditText = view.findViewById(R.id.et_time)
        val venueTextView: EditText = view.findViewById(R.id.et_venue)
        val conslay : ConstraintLayout = view.findViewById(R.id.et_cl_event)
        val llScore: LinearLayoutCompat = view.findViewById(R.id.et_ll_score)
        val scoreA : EditText = view.findViewById(R.id.et_tv_scoreA)
        val scoreB : EditText = view.findViewById(R.id.et_tv_scoreB)
        val wicketsA : EditText = view.findViewById(R.id.et_tv_wicketsA)
        val wicketsB : EditText = view.findViewById(R.id.et_tv_wicketsB)

        private val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val match = etGameslist[position]
                    val updatedValue = (view as EditText).text.toString()
                    val fieldUpdated = when (view.id) {
                        R.id.et_league -> "league"
                        R.id.et_team1name -> "teamAname"
                        R.id.et_team2name -> "teamBname"
                        R.id.et_date -> "date"
                        R.id.et_time -> "time"
                        R.id.et_venue -> "venue"
                        R.id.et_tv_wicketsA -> "score/wicketsA"
                        R.id.et_tv_wicketsB -> "score/wicketsB"
                        R.id.et_tv_scoreA -> "score/scoreA"
                        R.id.et_tv_scoreB -> "score/scoreB"
                        else -> ""
                    }
                    if (fieldUpdated.isNotBlank()) {
                        listener.onUpdateField(currentFragment, match.key, fieldUpdated, updatedValue)
                    }
                }
            }
        }

        init {
            leagueTextView.onFocusChangeListener = onFocusChangeListener
            team1Name.onFocusChangeListener = onFocusChangeListener
            team2Name.onFocusChangeListener = onFocusChangeListener
            wicketsA.onFocusChangeListener = onFocusChangeListener
            wicketsB.onFocusChangeListener = onFocusChangeListener
            scoreA.onFocusChangeListener = onFocusChangeListener
            scoreB.onFocusChangeListener = onFocusChangeListener
            venueTextView.onFocusChangeListener = onFocusChangeListener
            timeTextView.onFocusChangeListener = onFocusChangeListener
            dateTextView.onFocusChangeListener = onFocusChangeListener
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditableIndividualEventAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_editable_event, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = etGameslist[position]
        holder.apply {
            leagueTextView.text = match.league.toEditable()
            team1Name.text = match.teamAname.toEditable()
            team2Name.text = match.teamBname.toEditable()
            dateTextView.text = match.date.toEditable()
            timeTextView.text = match.time.toEditable()
            venueTextView.text = match.venue.toEditable()
            scoreA.text = match.score.scoreA.toEditable()
            scoreB.text = match.score.scoreB.toEditable()
            wicketsA.text = match.score.wicketsA.toEditable()
            wicketsB.text = match.score.wicketsB.toEditable()

            if (match.teamAImage != "") {
                Glide.with(holder.team1Image.context).load(match.teamAImage).apply(
                    RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.img)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                ).into(holder.team1Image)
            }
            if (match.teamBImage != "") {
                Glide.with(holder.team2Image.context).load(match.teamBImage).apply(
                    RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.img)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                ).into(holder.team2Image)
            }
        }

        holder.conslay.setOnClickListener{
            holder.llScore.visibility =
                if(holder.llScore.visibility == View.GONE){
                    View.VISIBLE
                }else {
                    View.GONE
                }
        }

        holder.buttonFav1.setOnClickListener {
            if(holder.buttonFav1.isChecked && holder.buttonFav2.isChecked){
                holder.buttonFav1.isChecked = true
                holder.buttonFav2.isChecked = false
            }
        }
        holder.buttonFav2.setOnClickListener {
            if(holder.buttonFav2.isChecked && holder.buttonFav1.isChecked){
                holder.buttonFav2.isChecked = true
                holder.buttonFav1.isChecked = false
            }
        }

    }


    override fun getItemCount(): Int {
        return etGameslist.size
    }

    fun setData(newData: List<MatchData>) {
        val sizeBefore = etGameslist.size
        etGameslist = newData
        val sizeAfter = newData.size
        notifyItemRangeChanged(0, min(sizeBefore, sizeAfter))
        notifyItemRangeInserted(min(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
        notifyItemRangeRemoved(max(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}