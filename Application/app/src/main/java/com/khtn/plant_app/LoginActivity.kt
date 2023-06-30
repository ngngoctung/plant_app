package com.khtn.plant_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myPref: SessionManager
    private var db = Firebase.firestore
    private var TAG = "TEST_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)

        // Initialize user variable
        var email: String
        var password: String
        var fullName = ""

        // Check login
        checkLogin()

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Access a Cloud Firestore instance from your Activity

        // Initialize Email, Password textField
        val tfEmail = binding.textfieldEmail
        val tfPassword = binding.textfieldPassword

        // Reset state input when have change on it
        tfEmail.editText?.doOnTextChanged { _, _, _, _ ->
            tfEmail.error = null
        }
        tfPassword.editText?.doOnTextChanged { _, _, _, _ ->
            tfPassword.error = null
        }

        // Check remember me
        if(myPref.isRememberMe()!!)
        {
            Log.d(TAG, "Set text because have remember me")
            tfEmail.editText?.setText(myPref.getUserName().toString())
            tfPassword.editText?.setText(myPref.getPassword().toString())
        }
        else
        {
            Log.d(TAG, "Set text failed because don't have remember me")
        }


        binding.textviewSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener{
            email = tfEmail.editText?.text.toString().trim()
            password = tfPassword.editText?.text.toString().trim()

            // Set error when empty input email, password
            if (email.isEmpty()) {
                tfEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                tfEmail.error = "Invalid email address"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                tfPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Read data user from FireStore
            db.collection("Users").document(email)
                .get()
                .addOnSuccessListener {result ->
                    Log.d(TAG, "${result.id} => ${result.data}")
                    fullName = result.data?.get("name").toString()

                    // Update new password to Firestore if user has reset password
                    /*
                    val passwordFromFirestore = result.data?.get("password").toString()
                    if(password != passwordFromFirestore)
                    {
                        val mapUpdate = mapOf("password" to password)
                        db.collection("Users").document(email)
                            .update(mapUpdate)
                            .addOnSuccessListener {
                                Log.d(TAG, "Update password success")
                            }
                            .addOnFailureListener{
                                Log.w(TAG, "Update password failed")
                            }
                    }
                    */
                }
                .addOnFailureListener{exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }

            // Verify account using Firebase Auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    //Set user information to remember me
                    if (binding.checkboxRememberMe.isChecked){
                        Log.d(TAG, "Checked Box Remember me")
                        myPref.setRememberMe(true)
                    }
                    else{
                        Log.d(TAG, "Unchecked Box Remember me")
                        myPref.setRememberMe(false)
                    }

                    //Set myPref to keep login
                    myPref.setLogin(true)
                    myPref.setInfoUser(email, password, fullName)

                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this,
                        "Login failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.textviewForgotPassword.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    private fun checkLogin() {
        if(myPref.isLogin()!!){
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


