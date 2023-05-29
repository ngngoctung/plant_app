package com.khtn.plant_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khtn.plant_app.databinding.FragmentSpeciesBinding
import kotlinx.android.synthetic.main.fragment_species.*

class Species : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeciesBinding.inflate(inflater, container, false)

        val dataList = listOf(
            DataListView(R.drawable.red_cactus,
                "Red Cactus",
                "Plantae",
                "Cactaceae",
                "Cactus spines are produced \n" +
                        "from specialized structures..."),
            DataListView(R.drawable.fat_cactus,
                "Fat Cactus",
                "Plantae",
                "Cactaceae",
                "Cactus spines are produced \n" +
                        "from specialized structures..."),
            DataListView(R.drawable.circle_cactus,
                "Circle Cactus",
                "Plantae",
                "Cactaceae",
                "Cactus spines are produced \n" +
                        "from specialized structures..."),
            DataListView(R.drawable.saguaro_cactus,
                "Saguaro Cactus",
                "Plantae",
                "Cactaceae",
                "Cactus spines are produced \n" +
                        "from specialized structures..."),
            DataListView(R.drawable.barrel_cactus,
                "Barrel Cactus",
                "Plantae",
                "Cactaceae",
                "Cactus spines are produced \n" +
                        "from specialized structures...")
        )

        val listView = binding.listView
        val adapter = ListViewAdapter(requireContext(), dataList)
        listView.adapter = adapter



        return binding.root
    }
}