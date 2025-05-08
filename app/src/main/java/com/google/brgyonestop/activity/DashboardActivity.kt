package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val dashboard_name = findViewById<TextView>(R.id.textview_dashboard_name)
        val textview_announcement_title = findViewById<TextView>(R.id.textview_announcement_title)
        val textview_announcement_description = findViewById<TextView>(R.id.textview_announcement_description)
        val textview_announcement_date = findViewById<TextView>(R.id.textview_announcement_date)
        val dashboard_file_complaint = findViewById<LinearLayout>(R.id.dashboard_file_complaint)
        val linearlayout_dashboard_scheduleappointment = findViewById<LinearLayout>(R.id.linearlayout_dashboard_scheduleappointment)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token != null) {
            RetrofitClient.instance.getUserProfile("Bearer $token")
                .enqueue(object : Callback<ProfileResponse> {
                    override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val user = response.body()!!.user
                            val fullName = "${user.firstName} ${user.middleName} ${user.lastName} ${user.suffix}"
                                .trim()
                                .replace(Regex(" +"), " ")
                            dashboard_name.setText(fullName)

                            val call = RetrofitClient.instance.getAnnouncements(token)

                            call.enqueue(object : Callback<AnnouncementResponse> {
                                override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                                    if (response.isSuccessful && response.body() != null) {
                                        val announcementsList = response.body()!!.announcements

                                        if (announcementsList.isNotEmpty()) {
                                            val latestAnnouncement = announcementsList[0]
                                            textview_announcement_title.setText(latestAnnouncement.title)
                                            textview_announcement_description.setText(latestAnnouncement.description)
                                            textview_announcement_date.setText(latestAnnouncement.createdAt)

                                            Log.d("LATEST_ANNOUNCEMENT", "Title: ${latestAnnouncement.title}, Description: ${latestAnnouncement.description}")
                                        } else {
                                            Log.d("LATEST_ANNOUNCEMENT", "No announcements available")
                                        }
                                    } else {
                                        Log.e("ANNOUNCEMENT_ERROR", "Failed to load announcements: ${response.code()}")
                                    }
                                }
                                override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                                    Log.e("ANNOUNCEMENT_EXCEPTION", t.message.toString())
                                }
                            })

                        } else {
                            Toast.makeText(this@DashboardActivity, "Failed to load profile", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            dashboard_file_complaint.setOnClickListener {
                startActivity(
                    Intent(this, FileComplaintActivity::class.java)
                )
            }
            linearlayout_dashboard_scheduleappointment.setOnClickListener {
//                startActivity(
//                    Intent(this, Sc)
//                )
            }
        } else {
            Toast.makeText(this, "No token found. Please login again.", Toast.LENGTH_SHORT).show()
        }







//        var firstname = ""
//        var middlename = ""
//        var lastname = ""
//        var suffix = ""
//
//        intent?.let {
//            it.getStringExtra("firstname")?.let { fname ->
//                firstname = fname
//            }
//            it.getStringExtra("middlename")?.let { mname ->
//                middlename = mname
//            }
//            it.getStringExtra("lastname")?.let { lname ->
//                lastname = lname
//            }
//            it.getStringExtra("suffix")?.let { extrasuffix ->
//                suffix = extrasuffix
//            }
//
//        }
//
//        //commit and push the latest API to work the middlename part, test the dashboard must have the fullname displayed
//        //fix the name and notification part it must be fixed not moving the notification button.
//
//        val fullName = "$firstname $middlename $lastname $suffix".trim().replace(Regex(" +"), " ")
//
//        dashboard_name.text = fullName

    }
}