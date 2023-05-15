package com.khtn.plant_app

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.khtn.plant_app.databinding.ActivityHomePageBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var myPref: SessionManager
    private lateinit var ref: StorageReference
    private lateinit var navController: NavController
    private var TAG = "TEST_SAVE_URL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_view)
        setupWithNavController(bottomNavigationView, navController)
//        replaceFragment(Home())

        initMypPref()
        binding.fabAddNew.isEnabled = false
        //
        ref = FirebaseStorage.getInstance().getReference("images")

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

//        binding.bottomView.setOnItemSelectedListener{
//            when(it.itemId){
//                R.id.i_profile->replaceFragment(Profile())
//                R.id.i_home->replaceFragment(Home())
//                else->{}
//            }
//            true
//        }

    }

    private fun replaceFragment(fragment : Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101)
        {   var imageUrl = "test"
            var test_url = "test_url"
            val bundle = Bundle()
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            // Tạo một tên cho tệp ảnh
            val imageName = "PLANT_${timeStamp}" + ".jpg"
            val storageRef = ref.child("$imageName")
            Log.d(TAG, "Image name: $imageName")

            // Chuyển đổi bitmap thành byte array
            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()

            val uploadTask = storageRef.putBytes(imageData)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Lấy URL của ảnh từ Firebase Storage
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    binding.progressBarWaitAddingNew.visibility = View.GONE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    imageUrl = uri.toString()
                    // Ở đây, bạn có thể lưu URL vào Firestore hoặc làm bất kỳ điều gì khác với URL này
                    Log.d(TAG, "Image URL: $imageUrl")
                    bundle.putParcelable("imageBitmap", imageBitmap)
                    bundle.putString("url_image", imageUrl)
                    val newFragment = AddingNew()
                    newFragment.arguments = bundle
                    replaceFragment(newFragment)
                }
            }.addOnFailureListener { exception ->
                // Xảy ra lỗi trong quá trình tải lên
                Log.e(TAG, "Upload failed: $exception")
            }
            // Hiển thị ProgressBar
            binding.progressBarWaitAddingNew.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
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