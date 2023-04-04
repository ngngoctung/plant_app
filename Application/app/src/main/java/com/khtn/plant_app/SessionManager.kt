package com.khtn.plant_app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context?) {
    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGIN = "is_login"

    val pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.apply()
    }

    fun setUserName(username: String?){
        editor?.putString("username", username)
        editor?.apply()
    }

    fun isLogin(): Boolean?{
        return pref?.getBoolean(IS_LOGIN, false)
    }

    fun getUserName(): String?{
        return pref?.getString("username", "")
    }

    fun removeData(){
        editor?.clear()
        editor?.apply()
    }
}