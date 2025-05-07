package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.AnnouncementAdapter
import com.google.brgyonestop.R

class AnnouncementsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcements)

        // Sample data (replace with Firebase/API calls)
//        val announcements = listOf(
//            Announcement("Clean-Up Drive", "March 15, 2024", "Join us at the park at 8 AM!"),
//            Announcement("Water Interruption", "March 18, 2024", "Scheduled maintenance from 9 AM to 3 PM.")
//        )
//
//        // RecyclerView setup
//        val recyclerView = findViewById<RecyclerView>(R.id.rvAnnouncements)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = AnnouncementAdapter(announcements)
//
//        // Optional: Add dividers between items
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        )
    }
}