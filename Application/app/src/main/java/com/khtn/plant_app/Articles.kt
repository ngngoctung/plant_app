package com.khtn.plant_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentArticlesBinding

class Articles : Fragment(), AdapterRecycleView.MyClickListener {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var adapter: AdapterRecycleView
    private lateinit var recycleView: RecyclerView
    private lateinit var mContext: Context
    private var db = Firebase.firestore
    private lateinit var articlesArrayList: ArrayList<ArticlesData>
    private var TAG = "TEST_ARTICLES"

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
        var id: String? = ""
        var ten: String? = ""
        var url: String? = ""
        var desc: String? = ""
        var liked: Boolean? = false

        val docRef = db.collection("Articles")
        docRef.get()
            .addOnSuccessListener{querySnapshot ->
                articlesArrayList = arrayListOf<ArticlesData>()
                for (document in querySnapshot)
                {
                    if (document != null) {
                        id = document.getString("id").toString()
                        liked = document.getBoolean("liked")
                        ten = document.getString("title").toString()
                        url = document.getString("image_url").toString()
                        desc = document.getString("desc").toString()

                        val articles = ArticlesData(id, ten, url, desc, liked)
                        articlesArrayList.add(articles)
                    } else {

                    }
                }
                val layoutManager = LinearLayoutManager(context)
                recycleView = binding.recycleViewArticles
                recycleView.layoutManager = layoutManager
                recycleView.setHasFixedSize(true)
                adapter = AdapterRecycleView(articlesArrayList,this@Articles)
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
        Log.d(TAG, "Get url: " + articlesArrayList[position].image_url.toString())
        Log.d(TAG, "Get titile: " + articlesArrayList[position].title.toString())
        Log.d(TAG, "Get desc: " + articlesArrayList[position].desc.toString())
        val bundle = Bundle()
        bundle.putString("ID", articlesArrayList[position].id)
        articlesArrayList[position].liked?.let { bundle.putBoolean("Liked", it) }
        bundle.putString("ImageURL", articlesArrayList[position].image_url)
        bundle.putString("Title", articlesArrayList[position].title)
        bundle.putString("Desc", articlesArrayList[position].desc)
        val controller = findNavController()
        controller.navigate(R.id.action_articles_to_detailArticle, bundle)
    }
}