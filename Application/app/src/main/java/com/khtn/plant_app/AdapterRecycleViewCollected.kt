package com.khtn.plant_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterRecycleViewCollected(private val collectedList:  ArrayList<CollectedData>,
                                  val listener: MyClickListener):
    RecyclerView.Adapter<AdapterRecycleViewCollected.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collected, parent, false)
        return  MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return collectedList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = collectedList[position]
        holder.title.text = currentItem.title
        holder.desc.text = currentItem.desc
        Glide.with(holder.itemView.context)
            .load(currentItem.image_url)
            .into(holder.image)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.imageview_image_collected)
        val title : TextView = itemView.findViewById(R.id.textView_title_collected)
        val desc : TextView = itemView.findViewById(R.id.textView_desc_collected)

        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                listener.onClick(position)
            }
        }
    }

    interface MyClickListener{
        fun onClick(position: Int)
    }

}