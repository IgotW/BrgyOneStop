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
import com.google.brgyonestop.response.AppointmentResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAllAppointmentActivity : Activity() {
    private lateinit var listview_admin_allappointment: ListView
    private lateinit var appointmentAdapter: AdminAppointmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_appointment)

        val imageview_admin_allappointment_back = findViewById<ImageView>(R.id.imageview_admin_allappointment_back)
        listview_admin_allappointment = findViewById(R.id.listview_admin_allappointment)

        imageview_admin_allappointment_back.setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java)
            )
            finish()
        }
    }
    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            fetchAppointments(bearerToken)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchAppointments(token: String) {
        val call = RetrofitClient.instance.getAdminAppointments(token)

        call.enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(
                call: Call<AppointmentResponse>,
                response: Response<AppointmentResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // Get the list of complaints from the response
                    val appointments = response.body()!!.appointments

                    // Set up the adapter and bind it to the ListView
                    appointmentAdapter = AdminAppointmentAdapter(this@AdminAllAppointmentActivity, appointments)
                    listview_admin_allappointment.adapter = appointmentAdapter

                    listview_admin_allappointment.setOnItemClickListener { _, _, position, _ ->
                        val selectedAppointment = appointments[position]

                        startActivity(
                            Intent(this@AdminAllAppointmentActivity, AdminAppointmentDetailActivity::class.java).apply {
                                putExtra("appointmentId", selectedAppointment._id)
                                putExtra("title", selectedAppointment.title)
                                putExtra("purpose", selectedAppointment.purpose)
                                putExtra("date", selectedAppointment.date)
                                putExtra("created", selectedAppointment.createdAt)
                                putExtra("status", selectedAppointment.status)
                                putExtra("time", selectedAppointment.time)
                                putExtra("firstName", selectedAppointment.user.firstName)
                                putExtra("middleName", selectedAppointment.user.middleName)
                                putExtra("lastName", selectedAppointment.user.lastName)
                                putExtra("suffix", selectedAppointment.user.suffix)
                            }
                        )
                    }

                } else {
                    Toast.makeText(this@AdminAllAppointmentActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                Toast.makeText(this@AdminAllAppointmentActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}