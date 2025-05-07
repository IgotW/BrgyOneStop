package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.response.UserCountResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDashboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val imageview_admindashboard_createannouncement = findViewById<ImageView>(R.id.imageview_admindashboard_createannouncement)
        val textview_admindashboard_createannouncement = findViewById<TextView>(R.id.textview_admindashboard_createannouncement)
        val textview_dashboard_total_residents = findViewById<TextView>(R.id.textview_dashboard_total_residents)

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token != null) {
            val bearerToken = "Bearer $token"

            RetrofitClient.instance.getUserCount(bearerToken)
                .enqueue(object : Callback<UserCountResponse> {
                    override fun onResponse(
                        call: Call<UserCountResponse>,
                        response: Response<UserCountResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            val count = response.body()?.totalUsers ?: 0
                            textview_dashboard_total_residents.setText(count.toString())
                        } else {
                            Toast.makeText(this@AdminDashboardActivity, "Failed to load user count", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserCountResponse>, t: Throwable) {
                        Toast.makeText(this@AdminDashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
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
    }
}