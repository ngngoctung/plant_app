package com.khtn.plant_app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val controller = findNavController()
        controller.navigate(R.id.action_articles_to_detailArticle)
    }
}