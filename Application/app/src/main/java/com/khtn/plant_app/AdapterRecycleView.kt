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

class AdapterRecycleView(private val articleList:  ArrayList<ArticlesData>,
                         val listener: MyClickListener):
    RecyclerView.Adapter<AdapterRecycleView.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return  MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articleList[position]
        holder.title.text = currentItem.title
        Glide.with(holder.itemView.context)
            .load(currentItem.image_url)
            .into(holder.image)
        if(currentItem.liked == true)
        {
            holder.liked.setImageResource(R.drawable.heart_red)
        }
        else
        {
            holder.liked.setImageResource(R.drawable.heart)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.imageview_image_article)
        val title : TextView = itemView.findViewById(R.id.textView_title_article)
        var liked : ImageView = itemView.findViewById(R.id.imageView_heart)

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