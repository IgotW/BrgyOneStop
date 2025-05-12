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
import com.google.brgyonestop.adapter.ComplaintAdapter
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.ComplaintResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAllAnnouncementActivity : Activity() {
    private lateinit var listview_allannouncement: ListView
    private lateinit var announcementAdapter: AnnouncementAdapter
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_announcement)

        val imageview_allannouncement_back = findViewById<ImageView>(R.id.imageview_allannouncement_back)
        listview_allannouncement = findViewById(R.id.listview_allannouncement)

        imageview_allannouncement_back.setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java)
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
        val call = RetrofitClient.instance.getAnnouncements(token)

        call.enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(
                call: Call<AnnouncementResponse>,
                response: Response<AnnouncementResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val announcements = response.body()!!.announcements

                    announcementAdapter = AnnouncementAdapter(this@AdminAllAnnouncementActivity, announcements)
                    listview_allannouncement.adapter = announcementAdapter

                    listview_allannouncement.setOnItemClickListener { _, _, position, _ ->
                        val selectedComplaint = announcements[position]

                        startActivity(
                            Intent(this@AdminAllAnnouncementActivity, AdminAnnouncementDetailsActivity::class.java).apply {
                                putExtra("announcement_id", selectedComplaint._id)
                                putExtra("announcement_title", selectedComplaint.title)
                                putExtra("announcement_description", selectedComplaint.description)
                            }
                        )
                    }
                    listview_allannouncement.setOnItemLongClickListener { _, _, position, _ ->
                        val selectedAnnouncement = announcements[position]

                        // Show the confirmation dialog
                        AlertDialog.Builder(this@AdminAllAnnouncementActivity)
                            .setTitle("Delete Announcement")
                            .setMessage("Are you sure you want to delete this announcement?")
                            .setPositiveButton("Yes") { dialog, _ ->
                                deleteAnnouncement(selectedAnnouncement._id)
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()

                        true
                    }
                } else {
                    Toast.makeText(this@AdminAllAnnouncementActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Toast.makeText(this@AdminAllAnnouncementActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun deleteAnnouncement(id: String) {
        RetrofitClient.instance.deleteAnnouncement("Bearer $token", id)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AdminAllAnnouncementActivity, "Announcement deleted", Toast.LENGTH_SHORT).show()
                        fetchAnnouncements("Bearer $token")
                    } else {
                        Toast.makeText(this@AdminAllAnnouncementActivity, "Delete failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AdminAllAnnouncementActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}