package com.google.brgyonestop.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.adapter.AnnouncementAdapter
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAllAnnouncementActivity : Activity() {
    private lateinit var listview_allannouncement: ListView
    private lateinit var announcementAdapter: AnnouncementAdapter
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_all_announcement)

        val imageview_allannouncement_back = findViewById<ImageView>(R.id.imageview_allannouncement_back)
        listview_allannouncement = findViewById(R.id.listview_allannouncement)

        imageview_allannouncement_back.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            fetchAnnouncements(bearerToken)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchAnnouncements(token: String) {
        val call = RetrofitClient.instance.getUserAnnouncements(token)

        call.enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(
                call: Call<AnnouncementResponse>,
                response: Response<AnnouncementResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val announcements = response.body()!!.announcements

                    announcementAdapter = AnnouncementAdapter(this@UserAllAnnouncementActivity, announcements)
                    listview_allannouncement.adapter = announcementAdapter

                } else {
                    Toast.makeText(this@UserAllAnnouncementActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Toast.makeText(this@UserAllAnnouncementActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}