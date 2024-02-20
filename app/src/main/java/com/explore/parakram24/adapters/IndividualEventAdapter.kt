package com.explore.parakram24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.explore.parakram24.R
import com.explore.parakram24.fragments.MatchData
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import kotlin.math.max
import kotlin.math.min

class IndividualEventAdapter(private var gameslist: List<MatchData>): RecyclerView.Adapter<IndividualEventAdapter.ViewHolder>()  {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leagueTextView: TextView = view.findViewById(R.id.league)
        val team1Image: ImageView = view.findViewById(R.id.team1Image)
        val team1Name: TextView = view.findViewById(R.id.team1name)
        val buttonFav1: CheckBox = view.findViewById(R.id.button_fav1)
        val team2Image: ImageView = view.findViewById(R.id.team2Image)
        val team2Name: TextView = view.findViewById(R.id.team2name)
        val buttonFav2: CheckBox = view.findViewById(R.id.button_fav2)
        val dateTextView: TextView = view.findViewById(R.id.date)
        val timeTextView: TextView = view.findViewById(R.id.time)
        val venueTextView: TextView = view.findViewById(R.id.venue)
        val gamecard : CircularRevealCardView = view.findViewById(R.id.gameCard)
        val llScore: LinearLayoutCompat = view.findViewById(R.id.ll_score)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualEventAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.events, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = gameslist[position]
        holder.apply {
            leagueTextView.text = match.league
            team1Name.text = match.teamAname
            team2Name.text = match.teamBname
            dateTextView.text = "Date: "+match.date
            timeTextView.text = "Time: "+match.time
            venueTextView.text = "Venue: ${match.venue}"
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

        holder.gamecard.setOnClickListener{
            holder.llScore.visibility =
                if(holder.llScore.visibility == View.GONE){
                    View.VISIBLE
                }else {
                    View.GONE
                }
        }

    }

    override fun getItemCount(): Int {
        return gameslist.size
    }

    fun setData(newData: List<MatchData>) {
        val sizeBefore = gameslist.size
        gameslist = newData
        val sizeAfter = newData.size
        notifyItemRangeChanged(0, min(sizeBefore, sizeAfter))
        notifyItemRangeInserted(min(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
        notifyItemRangeRemoved(max(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
    }

}