package com.khtn.plant_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
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
import kotlinx.android.synthetic.main.item_textview.view.name_textview


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
        var alphabet: List<String> = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        var speciesNameList: MutableList<String> = mutableListOf()

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
                    Log.d(TAG, "plantname = " + plantname)
                    var firstName = ""
                    firstName = plantname.substring(0, plantname.lastIndexOf(' '))
                    Log.d(TAG, "Lastname = " + firstName)
                    name.add(i, firstName)
                    Log.d(TAG, name[i])
                    i += 1

                }
                var distinctList = name.distinct().sortedWith(String.CASE_INSENSITIVE_ORDER)

//                Log.d(TAG, "------------------------")
//                for(j in 0..(distinctList.size-1)){
//                    Log.d(TAG, distinctList[j])
//                }

                var flag: Int = 0   //flag keep position list
                var count = distinctList.size   //reduce the number of loop when end of distinctList's list
                for(l in 0..alphabet.size){
                    if(count == 0)
                        break
                    speciesNameList.add(flag, alphabet[l])
                    flag++
                    for(k in 0 until distinctList.size){
                        if(distinctList[k].startsWith(alphabet[l])) {
                            speciesNameList.add(flag, distinctList[k])
                            flag++
                            count--
                        } else {
                            continue
                        }

                    }
                }
//                Log.d(TAG, "------------------------")
//                for(j in 0..(distinctList.size-1)){
//                    Log.d(TAG, speciesNameList[j])
//                }
                val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, speciesNameList)
                binding.listView.adapter = arrayAdapter
                binding.listView.setOnItemClickListener { _, _, i, _ ->
                    Toast.makeText(mContext, "Item selected " + distinctList[i], Toast.LENGTH_SHORT).show()
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