package com.khtn.plant_app

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentHomeBinding



class Home : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var mContext: Context
    private lateinit var myPref: SessionManager
    private val REQUEST_IMAGE_CAPTURE = 1
    private val fullname = "name"
    private val avatar = "avatar"
    private var db = Firebase.firestore
    private var TAG = "TEST_HOME"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initMypPref()
        // Xu ly cho profile
        var email = myPref.getUserName().toString()
        val docRef = db.collection("Users").document(email)  //get emails in collection
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var fullname = document.getString(fullname).toString()  //get name from firebase
                    val firstSpace = fullname.indexOf(" ") // detect the first space character
                    val name: String = fullname.substring(0, firstSpace) // get everything upto the first space character
                    val avatar: String = document.getString(avatar).toString() //get link avatar
                    binding.textviewName.setText("Hello " + name + "," )
                    Glide.with(this)
                        .load(avatar)
                        .apply(RequestOptions().circleCrop())
                        .into(binding.imageviewAvatarHome)

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        // Ket thuc xu ly cho profile
        return binding.root
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Làm gì đó với imageBitmap, ví dụ: hiển thị lên ImageView
        }
    }



    private fun initMypPref() {
        myPref = SessionManager(mContext)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonArticles.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_home2_to_articles)
        }
        binding.buttonSpecies.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_home2_to_species)
        }
    }
}