package com.khtn.plant_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private var title: List<String>,
                        private var description: List<String>,
                        private var images: List<Int>)
                        : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>()
{
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView= itemView.findViewById(R.id.tv_title)
        val itemDescription: TextView = itemView.findViewById(R.id.tv_description)
        val itemImage: ImageView = itemView.findViewById(R.id.image_on_boarding)

        init {
            itemImage.setOnClickListener{View ->
                val position = adapterPosition
                Toast.makeText(itemView.context, "Position ${position + 1}",
                    Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return  Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page,
        parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDescription.text = description[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }
}