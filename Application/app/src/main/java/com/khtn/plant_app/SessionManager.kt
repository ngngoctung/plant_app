package com.khtn.plant_app

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

class SessionManager(context: Context) {
    private val PREF_NAME = "SharedPreferences"
    private val KEY_IS_LOGIN = "is_login"
    private val KEY_EMAIL = "email"
    private val KEY_FULL_NAME = "full_name"
    private val KEY_PASSWORD = "password"
    private val KEY_REMEMBER_ME = "remember_me"
    private val KEY_ON_BOARDING = "on_boarding"

    private val pref: SharedPreferences? = context.getSharedPreferences(PREF_NAME,
        Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = pref?.edit()

    fun setRememberMe(isRememberMe: Boolean) {
        editor?.putBoolean(KEY_REMEMBER_ME, isRememberMe)
        editor?.apply()
    }

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(KEY_IS_LOGIN, isLogin)
        editor?.apply()
    }

    fun setInfoUser(email: String, password: String, fullName: String){
        editor?.putString(KEY_EMAIL, email)
        editor?.putString(KEY_PASSWORD, password)
        editor?.putString(KEY_FULL_NAME, fullName)
        editor?.apply()
    }

    fun setOnBoarding(isOnBoarding: Boolean){
        editor?.putBoolean(KEY_ON_BOARDING, isOnBoarding)
        editor?.apply()
    }

    fun isRememberMe(): Boolean?{
        return pref?.getBoolean(KEY_REMEMBER_ME, false)
    }

    fun isLogin(): Boolean?{
        return pref?.getBoolean(KEY_IS_LOGIN, false)
    }

    fun getUserName(): String?{
        return pref?.getString(KEY_EMAIL, "")
    }

    fun getFullName(): String?{
        return pref?.getString(KEY_FULL_NAME, "")
    }

    fun getPassword(): String?{
        return pref?.getString(KEY_PASSWORD, "")
    }

    fun getOnBoarding(): Boolean? {
        return pref?.getBoolean(KEY_ON_BOARDING, false)
    }

    fun removeData(){
        editor?.putBoolean(KEY_IS_LOGIN, false)
        editor?.apply()
    }
}