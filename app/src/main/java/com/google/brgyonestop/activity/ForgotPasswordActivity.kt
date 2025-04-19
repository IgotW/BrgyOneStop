package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.brgyonestop.R

class ForgotPasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val image_f1backarrow = findViewById<ImageView>(R.id.imageview_f1backarrow)
        val button_resetpassword = findViewById<Button>(R.id.button_resetpassword)

        image_f1backarrow.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }

        button_resetpassword.setOnClickListener {
            startActivity(
                Intent(this, ForgotPassword2Activity::class.java)
            )
        }
    }
}
