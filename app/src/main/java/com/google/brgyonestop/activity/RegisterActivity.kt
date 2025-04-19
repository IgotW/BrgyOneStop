package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            startActivity(
                Intent(this, Register2Activity::class.java).apply {
                    putExtra("username", edittext_reg_username.text.toString())
                    putExtra("email", edittext_reg_email.text.toString())
                    putExtra("phone", edittext_reg_phonenumber.text.toString())
                    putExtra("password", edittext_reg_password.text.toString())
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