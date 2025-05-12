package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.AppointmentsCountResponse
import com.google.brgyonestop.response.ComplaintsCountResponse
import com.google.brgyonestop.response.UserCountResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDashboardActivity : Activity() {
    private lateinit var textview_admindashboard_total_residents: TextView
    private lateinit var textview_admindashboard_total_filedcomplaints: TextView
    private lateinit var textview_admindashboard_total_incidentreports: TextView
    private lateinit var textview_admindashboard_total_appointments: TextView
    private lateinit var textview_admindashboard_total_assistancerequest: TextView
    private lateinit var textview_admindashboard_announcement_title: TextView
    private lateinit var textview_admindashboard_announcement_description: TextView
    private lateinit var textview_announcement_date: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val imageview_admindashboard_createannouncement = findViewById<ImageView>(R.id.imageview_admindashboard_createannouncement)
        val textview_admindashboard_createannouncement = findViewById<TextView>(R.id.textview_admindashboard_createannouncement)
        textview_admindashboard_total_residents = findViewById(R.id.textview_admindashboard_total_residents)
        textview_admindashboard_total_filedcomplaints = findViewById(R.id.textview_admindashboard_total_filedcomplaints)
        textview_admindashboard_total_incidentreports = findViewById(R.id.textview_admindashboard_total_incidentreports)
        textview_admindashboard_total_appointments = findViewById(R.id.textview_admindashboard_total_appointments)
        textview_admindashboard_total_assistancerequest = findViewById(R.id.textview_admindashboard_total_assistancerequest)
        val textview_admindashboard_seemore = findViewById<TextView>(R.id.textview_admindashboard_seemore)
        val imageview_admindashboard_seemore = findViewById<ImageView>(R.id.imageview_admindashboard_seemore)
        val card_filedcomplaints = findViewById<CardView>(R.id.card_filedcomplaints)
        textview_admindashboard_announcement_title = findViewById(R.id.textview_admindashboard_announcement_title)
        textview_admindashboard_announcement_description = findViewById(R.id.textview_admindashboard_announcement_description)
        textview_announcement_date = findViewById(R.id.textview_announcement_date)
        val imageview_nav_residents = findViewById<ImageView>(R.id.imageview_nav_residents)

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token != null) {
            val bearerToken = "Bearer $token"

            fetchUserCount(bearerToken)
            fetchComplaintCount(bearerToken)
            fetchAppointmentCount(bearerToken)
            fetchLatestAnnouncement(bearerToken)

//            RetrofitClient.instance.getUserCount(bearerToken)
//                .enqueue(object : Callback<UserCountResponse> {
//                    override fun onResponse(
//                        call: Call<UserCountResponse>,
//                        response: Response<UserCountResponse>
//                    ) {
//                        if (response.isSuccessful && response.body()?.success == true) {
//                            val count = response.body()?.totalUsers ?: 0
//                            textview_admindashboard_total_residents.setText(count.toString())
//                        } else {
//                            Toast.makeText(this@AdminDashboardActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<UserCountResponse>, t: Throwable) {
//                        Toast.makeText(this@AdminDashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                    }
//                })
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }

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
        card_filedcomplaints.setOnClickListener {
            startActivity(
                Intent(this, AdminAllFiledComplaintsActivity::class.java)
            )
        }
        textview_admindashboard_seemore.setOnClickListener {
            startActivity(
                Intent(this, AdminAllAnnouncementActivity::class.java)
            )
        }
        imageview_admindashboard_seemore.setOnClickListener {
            startActivity(
                Intent(this, AdminAllAnnouncementActivity::class.java)
            )
        }
        imageview_nav_residents.setOnClickListener {
            startActivity(
                Intent(this, AdminResidentsListActivity::class.java)
            )
        }
    }
    private fun fetchUserCount (token: String){
        RetrofitClient.instance.getUserCount(token)
            .enqueue(object : Callback<UserCountResponse> {
                override fun onResponse(
                    call: Call<UserCountResponse>,
                    response: Response<UserCountResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val count = response.body()?.totalUsers ?: 0
                        textview_admindashboard_total_residents.setText(count.toString())
                    } else {
                        Toast.makeText(this@AdminDashboardActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserCountResponse>, t: Throwable) {
                    Toast.makeText(this@AdminDashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchComplaintCount (token: String){
        RetrofitClient.instance.getComplaintsCount(token)
            .enqueue(object : Callback<ComplaintsCountResponse> {
                override fun onResponse(
                    call: Call<ComplaintsCountResponse>,
                    response: Response<ComplaintsCountResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val count = response.body()?.totalComplaint ?: 0
                        textview_admindashboard_total_filedcomplaints.setText(count.toString())
                    } else {
                        Toast.makeText(this@AdminDashboardActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ComplaintsCountResponse>, t: Throwable) {
                    Toast.makeText(this@AdminDashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun fetchAppointmentCount (token: String){
        RetrofitClient.instance.getAppointmentsCount(token)
            .enqueue(object : Callback<AppointmentsCountResponse> {
                override fun onResponse(
                    call: Call<AppointmentsCountResponse>,
                    response: Response<AppointmentsCountResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val count = response.body()?.totalAppointment ?: 0
                        textview_admindashboard_total_appointments.setText(count.toString())
                    } else {
                        Toast.makeText(this@AdminDashboardActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AppointmentsCountResponse>, t: Throwable) {
                    Toast.makeText(this@AdminDashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun fetchLatestAnnouncement(token: String) {
        RetrofitClient.instance.getAnnouncements(token)
            .enqueue(object : Callback<AnnouncementResponse> {
                override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val announcementsList = response.body()!!.announcements
                        if (announcementsList.isNotEmpty()) {
                            val latest = announcementsList[0]

                            textview_admindashboard_announcement_title.setText(latest.title)
                            textview_admindashboard_announcement_description.setText(latest.description)
                            textview_announcement_date.setText(latest.createdAt)
                        }
                    }
                }

                override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                    Toast.makeText(this@AdminDashboardActivity, "Failed to load announcements", Toast.LENGTH_SHORT).show()
                }
            })
    }


}