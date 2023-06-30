package com.khtn.plant_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentDetailArticleBinding
import com.khtn.plant_app.databinding.FragmentHomeBinding

class DetailArticle : Fragment() {
    private lateinit var binding: FragmentDetailArticleBinding
    private var db = Firebase.firestore
    private var TAG = "TEST_ARTICLES_DETAIL"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        val ID = arguments?.getString("ID").toString()
        val Title = arguments?.getString("Title")
        val imageURL = arguments?.getString("ImageURL")
        val Desc = arguments?.getString("Desc")
        var Liked = arguments?.getBoolean("Liked")

        binding.textviewDetailTitle.text = Title
        binding.textviewDetailDesc.text = Desc
        Glide.with(this)
            .load(imageURL)
            .into(binding.imageviewDetailImage)
        if(Liked == true)
        {
            binding.imageviewIconHeart.setImageResource(R.drawable.icon_heart)
        }
        else
        {
            binding.imageviewIconHeart.setImageResource(R.drawable.icon_heart_gray)
        }

        binding.imageviewIconHeart.setOnClickListener{
            val docRef = db.collection("Articles").document(ID)
            if(Liked == true)
            {
                docRef.update("liked", false)
                    .addOnSuccessListener {
                        Log.d(TAG, "Set field LIKE OK")
                        binding.imageviewIconHeart.setImageResource(R.drawable.icon_heart_gray)
                        Liked = false

                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Set field LIKE FAILED")
                    }
            }
            else
            {
                docRef.update("liked", true)
                    .addOnSuccessListener {
                        Log.d(TAG, "Set field LIKE OK")
                        binding.imageviewIconHeart.setImageResource(R.drawable.icon_heart)
                        Liked = true
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Set field LIKE FAILED")
                    }
            }

        }
        return binding.root
    }
}