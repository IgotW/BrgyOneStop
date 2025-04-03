package com.google.brgyonestop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class Register2Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val button_register2_next = findViewById<Button>(R.id.button_register2_next)
        val button_register2_cancel = findViewById<Button>(R.id.button_register2_cancel)

        button_register2_next.setOnClickListener {
            startActivity(
                Intent(this, Register3Activity::class.java)
            )
        }

        button_register2_cancel.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }
}