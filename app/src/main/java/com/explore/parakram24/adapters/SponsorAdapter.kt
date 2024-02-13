package com.explore.parakram24.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.explore.parakram24.R
import kotlin.math.max
import kotlin.math.min

class SponsorAdapter(
    private var sponsorlist: List<SponsorData>,
    private val onItemClick: (String) -> Unit) :RecyclerView.Adapter<SponsorAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sponsor: ImageView = view.findViewById(R.id.iv_item_sponsor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_sponsor, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return sponsorlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sponsor = sponsorlist[position]
        if (sponsor.image != "") {
            Glide.with(holder.sponsor.context).load(sponsor.image).apply(
                RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.img)
                    .diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )
            ).into(holder.sponsor)
        }

        holder.sponsor.setOnClickListener{
            if(sponsor.link != ""){
                onItemClick.invoke(sponsor.link)
            }
        }
    }

    fun setData(newData: List<SponsorData>) {
        val sizeBefore = sponsorlist.size
        sponsorlist = newData
        val sizeAfter = newData.size
        notifyItemRangeChanged(0, min(sizeBefore, sizeAfter))
        notifyItemRangeInserted(min(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
        notifyItemRangeRemoved(max(sizeBefore, sizeAfter), max(sizeBefore, sizeAfter) - min(sizeBefore, sizeAfter))
    }

}

data class SponsorData(val image: String, val link: String)
