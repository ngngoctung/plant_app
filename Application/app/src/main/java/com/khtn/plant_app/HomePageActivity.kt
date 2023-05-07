package com.khtn.plant_app

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.khtn.plant_app.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var myPref: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        initMypPref()
        binding.fabAddNew.isEnabled = false

        // check permission to open camera
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                100)
        }
        else
        {
            binding.fabAddNew.isEnabled = true
        }

        binding.fabAddNew.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 101)
        }

        binding.bottomView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.i_profile->replaceFragment(Profile())
                R.id.i_home->replaceFragment(Home())
                else->{}
            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101)
        {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val bundle = Bundle()
            bundle.putParcelable("imageBitmap", imageBitmap)
            val newFragment = AddingNew()
            newFragment.arguments = bundle
            replaceFragment(newFragment)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            binding.fabAddNew.isEnabled = false
        }
    }
    private fun initMypPref() {
        myPref = SessionManager(this)
    }
}