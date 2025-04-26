package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.brgyonestop.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edittext_reg_username = findViewById<EditText>(R.id.edittext_reg_username)
        val edittext_reg_email = findViewById<EditText>(R.id.edittext_reg_email)
        val edittext_reg_phonenumber = findViewById<EditText>(R.id.edittext_reg_phonenumber)
        val edittext_reg_password = findViewById<EditText>(R.id.edittext_reg_password)
        val edittext_reg_confirmpassword = findViewById<EditText>(R.id.edittext_reg_confirmpassword)
        val button_register1_next = findViewById<Button>(R.id.button_register1_next)
        val button_register1_cancel = findViewById<Button>(R.id.button_register1_cancel)

        button_register1_next.setOnClickListener {
            val username = edittext_reg_username.text.toString()
            val email = edittext_reg_email.text.toString()
            val phone = edittext_reg_phonenumber.text.toString()
            val password = edittext_reg_password.text.toString()
            val confirmpassword = edittext_reg_confirmpassword.text.toString()

            var hasError = false

            if (username.isEmpty()) {
                edittext_reg_username.error = "Username is required"
                hasError = true
            }

            if (email.isEmpty()) {
                edittext_reg_email.error = "Email is required"
                hasError = true
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edittext_reg_email.error = "Enter a valid email address"
                hasError = true
            }

            if (phone.isEmpty()) {
                edittext_reg_phonenumber.error = "Phone number is required"
                hasError = true
            }

            if (password.isEmpty()) {
                edittext_reg_password.error = "Password is required"
                hasError = true
            }

            if (confirmpassword.isEmpty()) {
                edittext_reg_confirmpassword.error = "Please confirm your password"
                hasError = true
            } else if (password != confirmpassword) {
                edittext_reg_confirmpassword.error = "Passwords do not match"
                hasError = true
            }

            if (hasError) return@setOnClickListener

            startActivity(
                Intent(this, Register2Activity::class.java).apply {
                    putExtra("username", username)
                    putExtra("email", email)
                    putExtra("phone", phone)
                    putExtra("password", password)
                }
            )
        }


        button_register1_cancel.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }
}