package com.khtn.plant_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentSpeciesBinding


class Species : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var myPref: SessionManager
    private lateinit var mContext: Context
    private var db = Firebase.firestore
    private val plant = "name"


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        myPref = SessionManager(mContext)

        var name:MutableList<String> = mutableListOf()
        var listSpecies:MutableList<String> = mutableListOf()


        var i = 0
        db.collection("Plants")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
                    var plantname = document.id
                    if(plantname.startsWith(" ", 0)){  //check prefix is space
                        plantname = plantname.substring(1).trim()
                    }
                    Log.d(TAG, "plantname = $plantname")
                    var lastName = ""
                    lastName = plantname.substring(0, plantname.indexOf(" ")).toLowerCase().capitalize()
                    Log.d(TAG, "Lastname = $lastName")
                    name.add(i, lastName)
                    Log.d(TAG, name[i])
                    i += 1

                }

                listSpecies = name.distinct().sortedWith(String.CASE_INSENSITIVE_ORDER) as MutableList<String>
                Log.d(TAG, listSpecies[0] + listSpecies[1] + listSpecies[2] + listSpecies[3])
                val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, listSpecies)
                Log.d(TAG, "===================")
                binding.listView.adapter = arrayAdapter
                binding.listView.setOnItemClickListener { _, _, i, _ ->
                    Toast.makeText(mContext, "Item selected " + listSpecies[i], Toast.LENGTH_SHORT).show()
                }

                Log.d(TAG, "===================")
                for (i in listSpecies.indices){
                    Log.d("PRINT", listSpecies[i])
                }


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }





        return binding.root
    }


    private fun initMypPref() {
        myPref = SessionManager(mContext)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


}