package com.khtn.plant_app

import android.R.attr
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        val name = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        val colRef = db.collection("Plants")
        var i = 0

            colRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var plantname = document.id.toString()
                    Log.d(TAG, plant)
                    if(plantname.startsWith(" ", 0)){  //check prefix is space
                        plantname = plantname.substring(1).trim()
                    }
                    var lastName: String = ""
                    lastName = plantname.substring(plantname.lastIndexOf(" ") + 1)
                    Log.d(TAG, lastName)
                    name.set(i, lastName)
                    Log.d(TAG, "${name[i]}")
                    i = i + 1
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        //binding.textView2.setText(colRef)



        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, name)
        binding.listView.adapter = arrayAdapter

        binding.listView.setOnItemClickListener{ adapterView, view, i, l ->
            Toast.makeText(mContext, "Item selected " + name[i], Toast.LENGTH_SHORT).show()
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