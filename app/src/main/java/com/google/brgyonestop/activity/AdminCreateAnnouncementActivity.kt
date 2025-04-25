package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.brgyonestop.R

class AdminCreateAnnouncementActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create_announcement)

        val imageview_create_back = findViewById<ImageView>(R.id.imageview_create_back)
        val edittext_create_title = findViewById<EditText>(R.id.edittext_create_title)
        val textinputedittext_create_description = findViewById<TextInputEditText>(R.id.textinputedittext_create_description)
        val button_create_post = findViewById<Button>(R.id.button_create_post)
    }
}