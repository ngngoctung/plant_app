package com.khtn.plant_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class OnBoardingActivity : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        val indicator  = findViewById<CircleIndicator3>(R.id.indicator)
        var view_pager2: ViewPager2 = findViewById(R.id.view_pager2)

        postToList()
        view_pager2.adapter = ViewPagerAdapter(titleList, descriptionList, imageList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        indicator.setViewPager(view_pager2)
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
}