package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.brgyonestop.R

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textview_forgotpassword = findViewById<TextView>(R.id.textview_forgotpassword)
        val textview_register = findViewById<TextView>(R.id.textview_register)
        val button_login = findViewById<Button>(R.id.button_login)

        textview_register.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        textview_forgotpassword.setOnClickListener {
            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )
        }
        button_login.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }
    }
}