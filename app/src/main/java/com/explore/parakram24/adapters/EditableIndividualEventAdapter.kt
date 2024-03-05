package com.explore.parakram24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.explore.parakram24.R
import com.explore.parakram24.MatchData
import com.explore.parakram24.fragments.OnFieldUpdateListener
import kotlin.math.max
import kotlin.math.min

class EditableIndividualEventAdapter(private var etGamesList: List<MatchData>, private val listener : OnFieldUpdateListener): RecyclerView.Adapter<EditableIndividualEventAdapter.ViewHolder>()  {
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
        val constraintLayout : ConstraintLayout = view.findViewById(R.id.cl_event)
        val llScore: LinearLayoutCompat = view.findViewById(R.id.ll_score)
        val leftField1 : TextView = view.findViewById(R.id.tv_leftField1)
        val leftField2 : TextView = view.findViewById(R.id.tv_leftField2)
        val leftField3 : TextView = view.findViewById(R.id.tv_leftField3)
        val field1 : TextView = view.findViewById(R.id.tv_field1)
        val field2 : TextView = view.findViewById(R.id.tv_field2)
        val field3 : TextView = view.findViewById(R.id.tv_field3)
        val rightField1 : TextView = view.findViewById(R.id.tv_rightField1)
        val rightField2 : TextView = view.findViewById(R.id.tv_rightField2)
        val rightField3 : TextView = view.findViewById(R.id.tv_rightField3)
        val update : AppCompatImageButton = view.findViewById(R.id.et_btn_update)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditableIndividualEventAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_editable_event_new, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = etGamesList[position]
        holder.apply {

            val date = "Date: "+match.date
            val time = "Time: "+match.time
            val venue = "Venue: ${match.venue}"

            leagueTextView.text = match.league
            team1Name.text = match.teamAname
            team2Name.text = match.teamBname
            dateTextView.text = date
            timeTextView.text = time
            venueTextView.text = venue
            leftField1.text = match.score.leftField1
            field1.text = match.score.field1
            rightField1.text = match.score.rightField1
            leftField2.text = match.score.leftField2
            field2.text = match.score.field2
            rightField2.text = match.score.rightField2
            leftField3.text = match.score.leftField3
            field3.text = match.score.field3
            rightField3.text = match.score.rightField3

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

        holder.constraintLayout.setOnClickListener{
            holder.llScore.visibility =
                if(holder.llScore.visibility == View.GONE){
                    View.VISIBLE
                }else {
                    View.GONE
                }
        }

        holder.update.setOnClickListener {
            listener.openDialog(match)
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
        return etGamesList.size
    }

    fun setData(newData: List<MatchData>) {
        val sizeBefore = etGamesList.size
        etGamesList = newData
        val sizeAfter = newData.size
        notifyItemRangeChanged(0, min(sizeBefore, sizeAfter))
        notifyItemRangeInserted(min(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
        notifyItemRangeRemoved(max(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
    }

}
