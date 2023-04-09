package com.khtn.plant_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()
    private lateinit var myPref: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        myPref = SessionManager(this)
        val indicator  = findViewById<CircleIndicator3>(R.id.indicator)
        var view_pager2: ViewPager2 = findViewById(R.id.view_pager2)
        var button = findViewById<Button>(R.id.button_on_boarding)
        var pos = 0

        if(myPref.getOnBoarding()!!){
            startLoginActivity()
        }

        postToList()
        view_pager2.adapter = ViewPagerAdapter(titleList, descriptionList, imageList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicator.setViewPager(view_pager2)

        if(pos == 2){
            button.text = "SIGN IN"
        }
        else{
            button.text = "NEXT"
        }
        view_pager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pos = view_pager2.currentItem
                if(pos < titleList.size)
                {
                    button.text = "NEXT"
                }
                if(pos == 2)
                {
                    button.text = "SIGN UP"
                }
            }
        })
        button.setOnClickListener{
            if(pos == 2){
                myPref.setOnBoarding(true)
                startLoginActivity()
            }
            if(pos < titleList.size){
                pos++
                view_pager2.currentItem = pos
            }
        }
    }

    private fun addToList(title: String, description: String, image: Int ){
        titleList.add(title)
        descriptionList.add(description)
        imageList.add(image)
    }

    private fun postToList(){
        addToList("Identify Plants",
        "You can identify the plants you don't know \n" +
                "through your camera",
        R.drawable.onboarding1)

        addToList("Learn Many Plants Species",
        "Let's learn about the many plant species that \n" +
                "exist in this world",
        R.drawable.onboarding2)

        addToList("Read Many Articles About Plant",
            "Let's learn more about beautiful plants and read \n" +
                    "many articles about plants and gardening",
            R.drawable.onboarding3)
    }

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}