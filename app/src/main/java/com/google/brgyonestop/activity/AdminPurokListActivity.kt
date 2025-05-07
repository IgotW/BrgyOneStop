package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import com.google.brgyonestop.R

class AdminPurokListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_purok_list)

        val button_add_purok = findViewById<Button>(R.id.button_add_purok)
        val imageview_total_back = findViewById<ImageView>(R.id.imageview_total_back)
        val listview_total_purok = findViewById<ListView>(R.id.listview_total_purok)
    }
}