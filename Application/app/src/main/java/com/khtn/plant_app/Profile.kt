package com.khtn.plant_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.khtn.plant_app.databinding.FragmentHomeBinding
import com.khtn.plant_app.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.imageview_avatar_profile

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        Glide.with(this)
                        .load("https://firebasestorage.googleapis.com/v0/b/plant-app-14869.appspot.com/o/z4216752296330_9d3fbc2b0e5d8a448fd1eb19d6603727.jpg?alt=media&token=f3d8bace-959c-43f3-b0bf-271e587cb706")
                        .apply(RequestOptions().circleCrop())
                        .into(binding.imageviewAvatarProfile)
        return binding.root
    }
}