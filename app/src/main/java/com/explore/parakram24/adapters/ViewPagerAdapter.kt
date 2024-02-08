package com.explore.parakram24.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.explore.parakram24.R

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    val list = listOf(R.drawable.d1, R.drawable.d2,R.drawable.d3,R.drawable.d4,R.drawable.d5,R.drawable.d6)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_corousel,parent,false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_merchandise)
        Glide.with(holder.itemView.context)
            .load(list[position])
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.progress_animation)
            .centerCrop()
            .into(imageView)

    }
}