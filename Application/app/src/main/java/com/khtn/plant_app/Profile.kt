package com.khtn.plant_app

import android.Manifest
import android.content.Context
import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.FragmentProfileBinding
import java.util.Locale


class Profile : Fragment(), AdapterRecycleViewCollected.MyClickListener{
    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: AdapterRecycleViewCollected
    private lateinit var recycleView: RecyclerView
    private lateinit var collectedsArrayList: ArrayList<CollectedData>
    private lateinit var myPref: SessionManager
    private lateinit var mContext: Context
    private val fullname = "name"
    private val avatar = "avatar"
    private var db = Firebase.firestore
    private var TAG = "TEST_PROFILE"
    private var PERMISSION_ID = 100
    lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        myPref = SessionManager(mContext)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)

        imageProfile()
        // getLastLocation()

        binding.buttonSpecies.setOnClickListener {
            binding.buttonArticles.setBackgroundColor(getResources().getColor(R.color.while_background))
            binding.buttonArticles.setTextColor(getResources().getColor(R.color.gray_text))
            binding.buttonSpecies.setBackgroundColor(getResources().getColor(R.color.background_button))
            binding.buttonSpecies.setTextColor(getResources().getColor(R.color.white))
            binding.textViewTest.text = "Your collected Species"
            InitDataSpecies()
        }

        binding.buttonArticles.setOnClickListener {
            binding.buttonSpecies.setBackgroundColor(getResources().getColor(R.color.while_background))
            binding.buttonSpecies.setTextColor(getResources().getColor(R.color.gray_text))
            binding.buttonArticles.setBackgroundColor(getResources().getColor(R.color.background_button))
            binding.buttonArticles.setTextColor(getResources().getColor(R.color.white))
            binding.textViewTest.text = "Your collected Articles"
            InitDataArticle()
        }

        binding.imageViewLogout.setOnClickListener{
            myPref.setLogin(false)
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitDataSpecies()
    }

    private fun InitDataArticle() {
        var ten: String? = ""
        var url: String? = ""
        var desc: String? = ""
        var ID: String? = ""
        var liked: Boolean? = false

        val docRef = db.collection("Articles")
        docRef.get()
            .addOnSuccessListener{querySnapshot ->
                collectedsArrayList = arrayListOf<CollectedData>()
                for (document in querySnapshot)
                {
                    if (document != null) {
                        liked = document.getBoolean("liked")
                        ID = document.getString("id").toString()
                        ten = document.getString("title").toString()
                        url = document.getString("image_url").toString()
                        desc = document.getString("desc").toString()
                        if(liked == true)
                        {
                            val collected = CollectedData(ten, url, desc, liked, id=ID)
                            collectedsArrayList.add(collected)
                        }
                    } else {

                    }
                }
                val layoutManager = LinearLayoutManager(context)
                recycleView = binding.recycleViewCollected
                recycleView.layoutManager = layoutManager
                recycleView.setHasFixedSize(true)
                adapter = AdapterRecycleViewCollected(collectedsArrayList,this@Profile)
                recycleView.adapter = adapter
            }
            .addOnFailureListener{Exception ->

            }
    }

    private fun InitDataSpecies() {
        var ten: String? = ""
        var url: String? = ""
        var desc: String? = ""
        var family: String? = ""
        var kingdom: String? = ""
        var liked: Boolean? = false

        val docRef = db.collection("Plants")
        docRef.get()
            .addOnSuccessListener{querySnapshot ->
                collectedsArrayList = arrayListOf<CollectedData>()
                for (document in querySnapshot)
                {
                    if (document != null) {
                        liked = document.getBoolean("liked")
                        ten = document.getString("name").toString()
                        url = document.getString("image_url").toString()
                        family = document.getString("family").toString()
                        kingdom = document.getString("kingdom").toString()
                        desc = document.getString("desc").toString()
                        if(liked == true)
                        {
                            val collected = CollectedData(ten, url, desc, liked, family, kingdom)
                            collectedsArrayList.add(collected)
                        }
                    } else {

                    }
                }
                val layoutManager = LinearLayoutManager(context)
                recycleView = binding.recycleViewCollected
                recycleView.layoutManager = layoutManager
                recycleView.setHasFixedSize(true)
                adapter = AdapterRecycleViewCollected(collectedsArrayList,this@Profile)
                recycleView.adapter = adapter
            }
            .addOnFailureListener{Exception ->

            }
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

    //get the last location
    private fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                        var location: Location? = task.result
                        Log.d(TAG, "------------------------ False")
                        if(location == null){
                            getNewLocation()
                        }else{
//                            binding.textviewLocation.text = "Long: "+ location.longitude +
//                                    "\nLat: " + location.latitude
                            binding.textviewLocation.text = getCityName(location.latitude, location.longitude) + ", " +
                                 getCountryName(location.latitude, location.longitude)
                        }
                    }
                    return
                }

            }else{
                Toast.makeText(mContext,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    private fun getNewLocation(){
        var locationRequest: LocationRequest = LocationRequest.Builder(1000).apply {
            this.setPriority(PRIORITY_HIGH_ACCURACY)
            this.setMinUpdateDistanceMeters(2F)
            this.setMaxUpdateDelayMillis(0L)
            this.setMinUpdateIntervalMillis(0)
        }.build()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
            )
            return
        }
    }



    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation = p0.lastLocation
            if (lastLocation != null) {
                binding.textviewLocation.text = getCityName(lastLocation.latitude, lastLocation.longitude) + ", " +
                        getCountryName(lastLocation.latitude, lastLocation.longitude)
            }
        }
    }

    //check use permission
    private fun CheckPermission():Boolean{
        //this function will return a boolean | true: if we have permission| false if not
        if(
            ActivityCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED ||

            ActivityCompat.checkSelfPermission(mContext,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ){

            return true
        }
        return false

    }


    //get user permission
    private fun RequestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    //check location service of the device is enabled
    private fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    //get the city name
    private fun  getCityName(lat: Double,long: Double): String{
        var cityName:String = ""
        var geoCoder = Geocoder(mContext, Locale.getDefault())
        var Address = geoCoder.getFromLocation(lat,long,1)

        if (Address != null) {
            val maxAddressLine: Int = Address.get(0).getMaxAddressLineIndex()
            cityName = Address.get(0).adminArea
        }
        return cityName
    }

    //get the country name
    private fun  getCountryName(lat: Double,long: Double): String{
        var countryName:String = ""
        var geoCoder = Geocoder(mContext, Locale.getDefault())
        var Address = geoCoder.getFromLocation(lat,long,1)
        if (Address != null) {
            countryName = Address.get(0).countryName
        }
        return countryName
    }


    //check permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug: ", "You have the permission")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onClick(position: Int) {
        Log.d(TAG, "Get url: " + collectedsArrayList[position].image_url.toString())
        Log.d(TAG, "Get titile: " + collectedsArrayList[position].title.toString())
        Log.d(TAG, "Get desc: " + collectedsArrayList[position].desc.toString())
        val bundle = Bundle()
        collectedsArrayList[position].liked?.let { bundle.putBoolean("Liked", it) }
        bundle.putString("ImageURL", collectedsArrayList[position].image_url)
        bundle.putString("Title", collectedsArrayList[position].title)
        bundle.putString("ID", collectedsArrayList[position].id)
        bundle.putString("Name", collectedsArrayList[position].title)
        bundle.putString("Desc", collectedsArrayList[position].desc)
        bundle.putString("Family", collectedsArrayList[position].family)
        bundle.putString("Kingdom", collectedsArrayList[position].kingdom)
        val controller = findNavController()
        if( binding.textViewTest.text == "Your collected Species")
        {
            controller.navigate(R.id.action_profile_to_detailSpecies, bundle)
        }
        else
        {
            controller.navigate(R.id.action_profile_to_detailArticle, bundle)
        }

    }
}


