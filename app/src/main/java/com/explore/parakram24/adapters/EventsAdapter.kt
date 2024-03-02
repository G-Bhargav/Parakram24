package com.explore.parakram24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.explore.parakram24.EventData
import com.explore.parakram24.R
import kotlin.math.max
import kotlin.math.min

class EventsAdapter(
    private var eventsList :List<EventData>,
    private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val eventImage : ImageView = view.findViewById(R.id.iv_event)
        val eventName : TextView = view.findViewById(R.id.tv_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_events,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventsList[position]
        holder.eventName.text = event.name
        holder.eventImage.setOnClickListener{
            onItemClick.invoke(event.name)
        }
        if (event.image.isEmpty()) {
            holder.eventImage.setImageResource(R.drawable.ic_loading)
        } else {

            Glide.with(holder.eventImage.context).load(event.image).apply(
                RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.img)
                    .diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )
            ).into(holder.eventImage)
        }
    }

    fun setData(newData: List<EventData>) {
        val sizeBefore = eventsList.size
        eventsList = newData
        val sizeAfter = newData.size
        notifyItemRangeChanged(0, min(sizeBefore, sizeAfter))
        notifyItemRangeInserted(min(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
        notifyItemRangeRemoved(max(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
    }

}

