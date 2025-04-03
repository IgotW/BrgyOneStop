package com.google.brgyonestop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val button_register1_next = findViewById<Button>(R.id.button_register1_next)
        val button_register1_cancel = findViewById<Button>(R.id.button_register1_cancel)

        button_register1_next.setOnClickListener {
            startActivity(
                Intent(this, Register2Activity::class.java)
            )
        }

        button_register1_cancel.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }
}