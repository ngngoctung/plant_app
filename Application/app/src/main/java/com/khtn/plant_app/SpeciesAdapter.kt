package com.khtn.plant_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class SpeciesAdapter(private val species: MutableList<SpeciesClass>) :
    RecyclerView.Adapter<SpeciesViewHolder>() {

    private lateinit var binding: Species

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_species, parent, false)
        return SpeciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val speciesItem = species[position]
        holder.species_name_textview.text = speciesItem.nameSpecies

    }

    override fun getItemCount(): Int {
        return species.size
    }


}

class SpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val species_name_textview: TextView = itemView.findViewById(R.id.species_name)
}
