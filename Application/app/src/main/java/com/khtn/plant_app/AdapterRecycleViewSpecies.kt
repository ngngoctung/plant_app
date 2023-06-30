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

class AdapterRecycleViewSpecies(private val speciesList:  ArrayList<SpeciesData>,
                                val listener: MyClickListener):
    RecyclerView.Adapter<AdapterRecycleViewSpecies.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_species, parent, false)
        return  MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return speciesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = speciesList[position]
        holder.title.text = currentItem.name
        holder.desc.text = currentItem.desc
        Glide.with(holder.itemView.context)
            .load(currentItem.image_url)
            .into(holder.image)
        holder.kingdom.text = currentItem.kingdom
        holder.family.text = currentItem.family
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.imageview_image_species)
        val title : TextView = itemView.findViewById(R.id.textView_title_species)
        val desc : TextView = itemView.findViewById(R.id.textView_desc_species)
        val kingdom : TextView = itemView.findViewById(R.id.textView_kingdom_species_data)
        val family : TextView = itemView.findViewById(R.id.textView_family_species_data)

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