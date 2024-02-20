package com.explore.parakram24.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.explore.parakram24.R
import kotlin.math.max
import kotlin.math.min

class EventsAdapter(
    private var eventsList :List<EventData>,
    private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val event : ImageView = view.findViewById(R.id.iv_event)
        val eventname : TextView = view.findViewById(R.id.tv_event)
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
        Log.i("name",event.name)
        holder.eventname.text = event.name
        holder.event.setOnClickListener{
            onItemClick.invoke(event.name)
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

data class EventData(val name: String)
