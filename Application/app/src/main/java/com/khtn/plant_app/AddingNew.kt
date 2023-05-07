package com.khtn.plant_app

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khtn.plant_app.databinding.FragmentAddingNewBinding
import com.khtn.plant_app.databinding.FragmentHomeBinding

class AddingNew : Fragment() {
    private lateinit var binding: FragmentAddingNewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddingNewBinding.inflate(inflater, container, false)
        val imageBitmap = arguments?.getParcelable<Bitmap>("imageBitmap")
        binding.imageView3.setImageBitmap(imageBitmap)
        return binding.root
    }
}