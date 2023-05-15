package com.khtn.plant_app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecycleView(private val articleList:  ArrayList<ArticlesData>):
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
        holder.image.setImageBitmap(null)
        holder.title.text = currentItem.title
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.imageview_image_article)
        val title : TextView = itemView.findViewById(R.id.textView_title_article)
    }

}