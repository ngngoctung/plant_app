package com.khtn.plant_app

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.khtn.plant_app.databinding.FragmentSpeciesBinding
import kotlinx.android.synthetic.main.fragment_species.*

class Species : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)

        val listView = binding.listView
        val dataList = ArrayList<DataListView>()

        val plantsCollection = FirebaseFirestore.getInstance().collection("Plants")
        plantsCollection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val name = document.id
                    val kingdom = document.getString("kingdom").toString()
                    val family = document.getString("family").toString()
                    val imageURL = document.getString("image_url") ?: ""
                    val description = document.getString("desc").toString()

                    val myData = DataListView(
                        imageURL,
                        name,
                        kingdom,
                        family,
                        description
                    )
                    dataList.add(myData)
                }

                val adapter = ListViewAdapter(requireContext(), dataList)
                listView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting plants collection", exception)
            }

        return binding.root
    }
}