package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.testingapplication.EmergencyHelpActivity
import com.google.brgyonestop.BarangayDirectoryActivity
import com.google.brgyonestop.HistoryActivity
import com.google.brgyonestop.R
import com.google.brgyonestop.ReportIncidentActivity
import com.google.brgyonestop.TrackComplaintActivity
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

        val complaint = findViewById<LinearLayout>(R.id.filecomplaint)
        complaint.setOnClickListener{
            val intent = Intent(this, FileComplaintActivity::class.java)
            startActivity(intent)
        }
        val help = findViewById<LinearLayout>(R.id.emergencyhelp)
        help.setOnClickListener{
            val intent = Intent(this, EmergencyHelpActivity::class.java)
            startActivity(intent)
        }
        val appointment = findViewById<LinearLayout>(R.id.scheduleappointment)
        appointment.setOnClickListener{
            val intent = Intent(this, ScheduleAppointmentActivity::class.java)
            startActivity(intent)
        }
        val incident = findViewById<LinearLayout>(R.id.reportincident)
        incident.setOnClickListener{
            val intent = Intent(this, ReportIncidentActivity::class.java)
            startActivity(intent)
        }
        val track = findViewById<LinearLayout>(R.id.trackrequest)
        track.setOnClickListener{
            val intent = Intent(this, TrackComplaintActivity::class.java)
            startActivity(intent)
        }
        val directory = findViewById<LinearLayout>(R.id.calldirectory)
        directory.setOnClickListener{
            val intent = Intent(this, BarangayDirectoryActivity::class.java)
            startActivity(intent)
        }
        val home = findViewById<LinearLayout>(R.id.home_nav)
        home.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val inbox = findViewById<LinearLayout>(R.id.inbox_nav)
        inbox.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val call = findViewById<ImageView>(R.id.emergencyButton)
        call.setOnClickListener{
            val intent = Intent(this, EmergencyHelpActivity::class.java)
            startActivity(intent)
        }
        val history = findViewById<LinearLayout>(R.id.history_nav)
        history.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        val profile = findViewById<LinearLayout>(R.id.profile_nav)
        home.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

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
                    Intent(this, UserAllFileComplaintsActivity::class.java)
                )
            }
            linearlayout_dashboard_scheduleappointment.setOnClickListener {
//                startActivity(
//                    Intent(this, Sc)
//                )
            }

            val help = findViewById<LinearLayout>(R.id.emergencyhelp)
            help.setOnClickListener{
                val intent = Intent(this, EmergencyHelpActivity::class.java)
                startActivity(intent)
            }
            val incident = findViewById<LinearLayout>(R.id.reportincident)
            incident.setOnClickListener{
//                val intent = Intent(this, ReportIncidentActivity::class.java)
//                startActivity(intent)
            }
            val track = findViewById<LinearLayout>(R.id.trackrequest)
            track.setOnClickListener{
//                val intent = Intent(this, TrackComplaintActivity::class.java)
//                startActivity(intent)
            }
            val directory = findViewById<LinearLayout>(R.id.calldirectory)
            directory.setOnClickListener{
//                val intent = Intent(this, BarangayDirectoryActivity::class.java)
//                startActivity(intent)
            }
            val home = findViewById<LinearLayout>(R.id.home_nav)
            home.setOnClickListener{
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
            val inbox = findViewById<LinearLayout>(R.id.inbox_nav)
            inbox.setOnClickListener{
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
            val call = findViewById<ImageView>(R.id.emergencyButton)
            call.setOnClickListener{
                val intent = Intent(this, EmergencyHelpActivity::class.java)
                startActivity(intent)
            }
            val history = findViewById<LinearLayout>(R.id.history_nav)
            history.setOnClickListener{
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
            val profile = findViewById<LinearLayout>(R.id.profile_nav)
            profile.setOnClickListener{
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
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