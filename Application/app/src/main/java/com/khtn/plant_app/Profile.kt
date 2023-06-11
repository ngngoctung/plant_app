package com.khtn.plant_app

import android.content.Context
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
import com.khtn.plant_app.databinding.FragmentHomeBinding
import com.khtn.plant_app.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.imageview_avatar_profile

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var myPref: SessionManager
    private lateinit var mContext: Context
    private val fullname = "name"
    private val avatar = "avatar"
    private var db = Firebase.firestore
    private var TAG = "TEST_PROFILE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        myPref = SessionManager(mContext)
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
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}