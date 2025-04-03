package com.google.brgyonestop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPassword3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password3)

        val image_f3backarrow = findViewById<ImageView>(R.id.imageview_f3backarrow)
        val button_forgotsubmit = findViewById<Button>(R.id.button_forgotsubmit)

        image_f3backarrow.setOnClickListener {
            startActivity(
                Intent(this, ForgotPassword2Activity::class.java)
            )
        }

        button_forgotsubmit.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }
}