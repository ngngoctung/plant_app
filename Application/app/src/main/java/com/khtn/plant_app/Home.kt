package com.khtn.plant_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khtn.plant_app.databinding.FragmentHomeBinding

class Home : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var mContext: Context
    private lateinit var myPref: SessionManager

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
        return binding.root
    }

    private fun initMypPref() {
        myPref = SessionManager(mContext)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

}