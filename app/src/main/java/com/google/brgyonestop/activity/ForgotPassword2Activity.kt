package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.brgyonestop.R

class ForgotPassword2Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        val imageview_f2backarrow = findViewById<ImageView>(R.id.imageview_f2backarrow)
        val button_sendcode = findViewById<Button>(R.id.button_sendcode)

        imageview_f2backarrow.setOnClickListener {
            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )
        }

        button_sendcode.setOnClickListener {
            startActivity(
                Intent(this, ForgotPassword3Activity::class.java)
            )
        }

    }
}