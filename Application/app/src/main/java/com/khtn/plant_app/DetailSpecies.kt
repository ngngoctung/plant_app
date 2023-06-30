package com.khtn.plant_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentDetailArticleBinding
import com.khtn.plant_app.databinding.FragmentDetailSpeciesBinding

class DetailSpecies : Fragment() {
    private lateinit var binding: FragmentDetailSpeciesBinding
    private var db = Firebase.firestore
    private var TAG = "TEST_SPECIES_DETAIL"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailSpeciesBinding.inflate(inflater, container, false)
        val name = arguments?.getString("Name").toString()
        val imageURL = arguments?.getString("ImageURL")
        val family = arguments?.getString("Family")
        val kingdom = arguments?.getString("Kingdom")
        val Desc = arguments?.getString("Desc")
        var Liked = arguments?.getBoolean("Liked")

        binding.textviewDetailSpeciesTitle.text = name
        binding.textviewDetailSpeciesDesc.text = Desc
        binding.textViewKingdomSpeciesDetail.text = kingdom
        binding.textViewFamilySpeciesDetail.text = family
        Glide.with(this)
            .load(imageURL)
            .into(binding.imageviewDetailSpeciesImage)
        if(Liked == true)
        {
            binding.imageviewIconHeartSpecies.setImageResource(R.drawable.icon_heart)
        }
        else
        {
            binding.imageviewIconHeartSpecies.setImageResource(R.drawable.icon_heart_gray)
        }

        binding.imageviewIconHeartSpecies.setOnClickListener{
            val docRef = db.collection("Plants").document(name)
            if(Liked == true)
            {
                docRef.update("liked", false)
                    .addOnSuccessListener {
                        Log.d(TAG, "Set field LIKE OK")
                        binding.imageviewIconHeartSpecies.setImageResource(R.drawable.icon_heart_gray)
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
                        binding.imageviewIconHeartSpecies.setImageResource(R.drawable.icon_heart)
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