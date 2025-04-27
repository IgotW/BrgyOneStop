package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class AdminResidentProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_resident_profile)

        val imageview_residentprofile_back = findViewById<ImageView>(R.id.imageview_residentprofile_back)
        val textview_residentprofile_firstname = findViewById<TextView>(R.id.textview_residentprofile_firstname)
        val textview_residentprofile_middlename = findViewById<TextView>(R.id.textview_residentprofile_middlename)
        val textview_residentprofile_lastname = findViewById<TextView>(R.id.textview_residentprofile_lastname)
        val textview_residentprofile_suffix = findViewById<TextView>(R.id.textview_residentprofile_suffix)
        val textview_residentprofile_birthdate = findViewById<TextView>(R.id.textview_residentprofile_birthdate)
        val textview_residentprofile_age = findViewById<TextView>(R.id.textview_residentprofile_age)
        val textview_residentprofile_gender = findViewById<TextView>(R.id.textview_residentprofile_gender)
    }
}