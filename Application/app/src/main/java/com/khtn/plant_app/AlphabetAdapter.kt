package com.khtn.plant_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//class AlphabetAdapter(private val alphabet: MutableList<AlphabetClass>) :
//    RecyclerView.Adapter<AlphabetViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_alphabet, parent, false)
//        return AlphabetViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
//        val alphabet = alphabet[position]
//        holder.alphabet_name_textview.text = alphabet.alphabetChar
//        holder.alphabet_recyclerview.layoutManager = LinearLayoutManager(holder.itemView.context)
//        holder.alphabet_recyclerview.adapter = SpeciesAdapter(alphabet.nameSpecies as MutableList<SpeciesClass>)
//    }
//
//    override fun getItemCount(): Int {
//        return alphabet.size
//    }
//}
//
//class AlphabetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val alphabet_name_textview: TextView = itemView.findViewById(R.id.alphabet_textview)
//    val alphabet_recyclerview: RecyclerView = itemView.findViewById(R.id.alphabet_recyclerview)
//}

class AlphabetAdapter(private val classes: List<AlphabetClass>) :
    RecyclerView.Adapter<ClassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alphabet, parent, false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val classes = classes[position]
        holder.class_name_textview.text = classes.name
        holder.student_recyclerview.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.student_recyclerview.adapter = SpeciesAdapter(classes.student)
    }

    override fun getItemCount(): Int {
        return classes.size
    }
}

class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val class_name_textview: TextView = itemView.findViewById(R.id.class_name_textview)
    val student_recyclerview: RecyclerView = itemView.findViewById(R.id.student_recyclerview)
}