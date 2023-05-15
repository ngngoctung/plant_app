package com.khtn.plant_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentArticlesBinding
import com.khtn.plant_app.databinding.FragmentHomeBinding

class Articles : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var adapter: AdapterRecycleView
    private lateinit var recycleView: RecyclerView
    private var db = Firebase.firestore
    private lateinit var articlesArrayList: ArrayList<ArticlesData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitData()


    }

    private fun InitData() {
        var ten: String? = ""
        var url: String? = ""

        val docRef = db.collection("Articles")
        docRef.get()
            .addOnSuccessListener{querySnapshot ->
                articlesArrayList = arrayListOf<ArticlesData>()
                for (document in querySnapshot)
                {
                    if (document != null) {
                        ten = document.getString("title").toString()  //get name from firebase
                        url = document.getString("image_url").toString() //get link avatar

                        val articles = ArticlesData(ten,url)
                        articlesArrayList.add(articles)


                    } else {

                    }
                }
                val layoutManager = LinearLayoutManager(context)
                recycleView = binding.recycleViewArticles
                recycleView.layoutManager = layoutManager
                recycleView.setHasFixedSize(true)
                adapter = AdapterRecycleView(articlesArrayList)
                recycleView.adapter = adapter
            }
            .addOnFailureListener{Exception ->

            }
    }
}