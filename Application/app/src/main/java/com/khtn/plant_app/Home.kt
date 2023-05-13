package com.khtn.plant_app

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.khtn.plant_app.databinding.FragmentHomeBinding



class Home : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var mContext: Context
    private lateinit var myPref: SessionManager
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initMypPref()
        binding.buttonLogout2.setOnClickListener{
            myPref.removeData()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.onBackPressed()
        }

        binding.buttonSpecies.setOnClickListener{
            val fragment = Species()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_layout, fragment)?.commit()
        }

        binding.buttonArticles.setOnClickListener{
            val fragment = Species()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.frame_layout, fragment)?.commit()
        }

        binding.buttonCamera.setOnClickListener{
            dispatchTakePictureIntent()
        }


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

}