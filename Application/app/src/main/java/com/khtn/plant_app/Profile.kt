package com.khtn.plant_app

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationRequestCompat
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentProfileBinding
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import java.util.Locale


class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var myPref: SessionManager
    private lateinit var mContext: Context
    private val fullname = "name"
    private val avatar = "avatar"
    private var db = Firebase.firestore
    private var TAG = "TEST_PROFILE"
    private var PERMISSION_ID = 22
//    lateinit var locationRequest: LocationRequest

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        myPref = SessionManager(mContext)
        imageProfile()

        binding.buttonSpecies.setOnClickListener {
            binding.buttonArticles.setBackgroundColor(getResources().getColor(R.color.while_background))
            binding.buttonArticles.setTextColor(getResources().getColor(R.color.gray_text))
            binding.buttonSpecies.setBackgroundColor(getResources().getColor(R.color.background_button))
            binding.buttonSpecies.setTextColor(getResources().getColor(R.color.white))
            Toast.makeText(mContext,
                "Select Species Button",
                Toast.LENGTH_SHORT).show()
        }

        binding.buttonArticles.setOnClickListener {
            binding.buttonSpecies.setBackgroundColor(getResources().getColor(R.color.while_background))
            binding.buttonSpecies.setTextColor(getResources().getColor(R.color.gray_text))
            binding.buttonArticles.setBackgroundColor(getResources().getColor(R.color.background_button))
            binding.buttonArticles.setTextColor(getResources().getColor(R.color.white))
            Toast.makeText(mContext,
                "Select Articles Button",
                Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun imageProfile(){
        var email: String = myPref.getUserName().toString()
        Log.d(TAG, email)
        val docRef = db.collection("Users").document(email)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var fullname = document.getString(fullname).toString()  //get name from firebase
                    val avatar: String = document.getString(avatar).toString() //get link avatar
                    binding.textviewFullName.setText(fullname)
                    Glide.with(this)
                        .load(avatar)
                        .apply(RequestOptions().circleCrop())
                        .into(binding.imageviewAvatarProfile)

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private fun getLocation(){

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}


