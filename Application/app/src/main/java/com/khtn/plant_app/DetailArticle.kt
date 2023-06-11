package com.khtn.plant_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khtn.plant_app.databinding.FragmentDetailArticleBinding
import com.khtn.plant_app.databinding.FragmentHomeBinding

class DetailArticle : Fragment() {
    private lateinit var binding: FragmentDetailArticleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        val Title = arguments?.getString("Title")
        val imageURL = arguments?.getString("ImageURL")
        val Desc = arguments?.getString("Desc")

        binding.textviewDetailTitle.text = Title
        binding.textviewDetailDesc.text = Desc
        Glide.with(this)
            .load(imageURL)
            .into(binding.imageviewDetailImage)
        return binding.root
    }
}