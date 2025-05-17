package com.google.brgyonestop.activity

import android.app.Activity
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
import com.google.brgyonestop.adapter.AdminAppointmentAdapter
import com.google.brgyonestop.adapter.AppointmentAdapter
import com.google.brgyonestop.adapter.ComplaintAdapter
import com.google.brgyonestop.response.AppointmentResponse
import com.google.brgyonestop.response.ComplaintResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAllAppointmentsActivity : Activity() {
    private lateinit var listview_allappointment: ListView
    private lateinit var appointmentAdapter: AppointmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_all_appointments)

        val imageview_allappointment_back = findViewById<ImageView>(R.id.imageview_allappointment_back)
        listview_allappointment = findViewById(R.id.listview_allappointment)

        imageview_allappointment_back.setOnClickListener {
            startActivity(
                Intent(this, ScheduleAppointmentActivity::class.java)
            )
        }
    }
    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            fetchComplaints(bearerToken)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchComplaints(token: String) {
        val call = RetrofitClient.instance.getAppointments(token)

        call.enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // Get the list of complaints from the response
                    val appointments = response.body()!!.appointments

                    // Set up the adapter and bind it to the ListView
                    appointmentAdapter = AppointmentAdapter(this@UserAllAppointmentsActivity, appointments)
                    listview_allappointment.adapter = appointmentAdapter

                } else {
                    Toast.makeText(this@UserAllAppointmentsActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                Toast.makeText(this@UserAllAppointmentsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}