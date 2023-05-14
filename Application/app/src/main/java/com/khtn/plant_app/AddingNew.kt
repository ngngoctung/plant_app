package com.khtn.plant_app

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentAddingNewBinding
import com.khtn.plant_app.databinding.FragmentHomeBinding

class AddingNew : Fragment() {
    private lateinit var binding: FragmentAddingNewBinding
    private lateinit var mContext: Context
    private var db = Firebase.firestore
    private var TAG = "TEST_TAKE_URL"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddingNewBinding.inflate(inflater, container, false)
        // Init UI
        val tfName = binding.textfieldPlantName
        val tfKingDom = binding.textfieldPlantKingdom
        val tfFamily = binding.textfieldPlantFamily
        val tfDesc = binding.textfieldPlantDescription

        // Initialize user variable
        var name: String?
        var kingdom: String?
        var family: String?
        var desc: String?
        var imageURL: String?

        val imageBitmap = arguments?.getParcelable<Bitmap>("imageBitmap")
        imageURL = arguments?.getString("url_image").toString()
        Log.d(TAG, "Image URL: $imageURL")
        binding.imageView3.setImageBitmap(imageBitmap)

        binding.buttonOkAddNew.setOnClickListener{
            name = tfName.editText?.text.toString().trim()
            kingdom = tfKingDom.editText?.text.toString().trim()
            family = tfFamily.editText?.text.toString().trim()
            desc = tfDesc.editText?.text.toString().trim()

            if (name!!.isEmpty() || kingdom!!.isEmpty() || family!!.isEmpty() || desc!!.isEmpty()){
                Toast.makeText(mContext,
                    "Have a empty information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else
            {
                val plant_infor = hashMapOf(
                    "name" to name,
                    "kingdom" to kingdom,
                    "family" to family,
                    "image_url" to imageURL,
                    "desc" to desc
                )
                db.collection("Plants").document(name!!)
                    .set(plant_infor)
                    .addOnSuccessListener { Toast.makeText(mContext,
                        "Pushed", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener{ Toast.makeText(mContext,
                        "Error save infor plant on Database", Toast.LENGTH_SHORT).show() }
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}