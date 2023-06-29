package com.khtn.plant_app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentSpeciesBinding


class Species : Fragment(), AdapterRecycleViewSpecies.MyClickListener {
    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var adapter: AdapterRecycleViewSpecies
    private lateinit var recycleView: RecyclerView
    private lateinit var myPref: SessionManager
    private lateinit var mContext: Context
    private var db = Firebase.firestore
    private lateinit var speciesArrayList: ArrayList<SpeciesData>


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)
        myPref = SessionManager(mContext)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitData()
    }

    private fun InitData() {
        var name: String? = ""
        var url: String? = ""
        var desc: String? = ""
        var family: String? = ""
        var kingdom: String? = ""
        var liked: Boolean? = false

        val docRef = db.collection("Plants")
        docRef.get()
            .addOnSuccessListener{querySnapshot ->
                speciesArrayList = arrayListOf<SpeciesData>()
                for (document in querySnapshot)
                {
                    if (document != null) {
                        name = document.getString("name").toString()
                        liked = document.getBoolean("liked")
                        family = document.getString("family").toString()
                        kingdom = document.getString("kingdom").toString()
                        url = document.getString("image_url").toString()
                        desc = document.getString("desc").toString()

                        val species = SpeciesData(name, url, desc, family, kingdom, liked)
                        speciesArrayList.add(species)
                    } else {

                    }
                }
                val layoutManager = LinearLayoutManager(context)
                recycleView = binding.recycleViewSpecies
                recycleView.layoutManager = layoutManager
                recycleView.setHasFixedSize(true)
                adapter = AdapterRecycleViewSpecies(speciesArrayList,this@Species)
                recycleView.adapter = adapter
            }
            .addOnFailureListener{Exception ->

            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onClick(position: Int) {
//        Log.d(TAG, "Get url: " + articlesArrayList[position].image_url.toString())
//        Log.d(TAG, "Get titile: " + articlesArrayList[position].title.toString())
//        Log.d(TAG, "Get desc: " + articlesArrayList[position].desc.toString())
//        val bundle = Bundle()
//        bundle.putString("ID", speciesArrayList[position].name)
//        speciesArrayList[position].liked?.let { bundle.putBoolean("Liked", it) }
//        bundle.putString("ImageURL", speciesArrayList[position].image_url)
//        bundle.putString("Title", speciesArrayList[position].name)
//        bundle.putString("Desc", speciesArrayList[position].desc)
//        val controller = findNavController()
//        controller.navigate(R.id.action_articles_to_detailArticle, bundle)
    }
}