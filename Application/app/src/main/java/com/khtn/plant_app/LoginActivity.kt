package com.khtn.plant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myPref: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myPref = SessionManager(this)
        // Check login
        checkLogin()

        // Initialize Firebase Auth
        auth = Firebase.auth

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


        binding.buttonLogin.setOnClickListener{
            val email = tfEmail.editText?.text.toString()
            val password = tfPassword.editText?.text.toString()

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

            // Verify account using Firebase Auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    //Set myPref to keep login
                    myPref.setLogin(true)
                    myPref.setUserName(email)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this,
                        "Account does not exist",
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


