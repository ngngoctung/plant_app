package com.khtn.plant_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khtn.plant_app.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize Email, Password textField
        val tfEmailReset = binding.textfieldEmailReset

        tfEmailReset.editText?.doOnTextChanged { _, _, _, _ ->
            tfEmailReset.error = null
        }

        binding.buttonSendEmail.setOnClickListener{
            val emailReset = tfEmailReset.editText?.text.toString()

            // Set error when empty input email, password
            if (emailReset.isEmpty()) {
                tfEmailReset.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(emailReset).matches()){
                tfEmailReset.error = "Invalid email address"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(emailReset).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    Toast.makeText(this,
                        "Email sent successfully to reset your password",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this,
                        "Email does not exist",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}