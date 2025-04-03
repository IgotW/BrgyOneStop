package com.google.brgyonestop

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val textview_forgotpassword = findViewById<TextView>(R.id.textview_forgotpassword)
        val textview_register = findViewById<TextView>(R.id.textview_register)

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
    }
}