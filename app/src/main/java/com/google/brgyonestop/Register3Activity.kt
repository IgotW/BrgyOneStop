package com.google.brgyonestop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Register3Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)

        val button_register3_next = findViewById<Button>(R.id.button_register3_next)
        val button_register3_cancel = findViewById<Button>(R.id.button_register3_cancel)

        button_register3_next.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }

        button_register3_cancel.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }
}