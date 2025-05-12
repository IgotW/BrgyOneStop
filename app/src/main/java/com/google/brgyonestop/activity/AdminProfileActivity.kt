package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class AdminProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        val imageview_nav_residents = findViewById<ImageView>(R.id.imageview_nav_residents)
        val button_adminprofile_logout = findViewById<Button>(R.id.button_adminprofile_logout)
        val admin_home = findViewById<LinearLayout>(R.id.admin_home)

        imageview_nav_residents.setOnClickListener {
            startActivity(
                Intent(this, AdminResidentsListActivity::class.java)
            )
        }
        button_adminprofile_logout.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
        admin_home.setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java)
            )
        }
        imageview_nav_residents.setOnClickListener {
            startActivity(
                Intent(this, AdminResidentsListActivity::class.java)
            )
        }
    }
}