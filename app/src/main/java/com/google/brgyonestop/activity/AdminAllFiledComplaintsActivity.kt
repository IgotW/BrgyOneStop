package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class AdminAllFiledComplaintsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_filed_complaints)

        val imageview_allcomplaints_back = findViewById<ImageView>(R.id.imageview_allcomplaints_back)
        val listview_allcomplaints = findViewById<ListView>(R.id.listview_allcomplaints)
    }
}