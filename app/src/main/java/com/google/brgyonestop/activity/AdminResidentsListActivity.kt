package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class AdminResidentsListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_residents_list)

        val imageview_residentlist_back = findViewById<ImageView>(R.id.imageview_residentlist_back)
        val textview_residentlist_purokname = findViewById<TextView>(R.id.textview_residentlist_purokname)
        val listview_residentlist = findViewById<ListView>(R.id.listview_residentlist)
    }
}