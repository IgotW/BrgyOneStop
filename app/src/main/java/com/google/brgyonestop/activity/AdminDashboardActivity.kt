package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class AdminDashboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val imageview_admindashboard_createannouncement = findViewById<ImageView>(R.id.imageview_admindashboard_createannouncement)
        val textview_admindashboard_createannouncement = findViewById<TextView>(R.id.textview_admindashboard_createannouncement)

        textview_admindashboard_createannouncement.setOnClickListener {
            startActivity(
                Intent(this, AdminCreateAnnouncementActivity::class.java)
            )
        }
        imageview_admindashboard_createannouncement.setOnClickListener {
            startActivity(
                Intent(this, AdminCreateAnnouncementActivity::class.java)
            )
        }
    }
}