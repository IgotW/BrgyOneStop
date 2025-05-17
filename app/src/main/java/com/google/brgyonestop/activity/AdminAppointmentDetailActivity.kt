package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAppointmentDetailActivity : Activity() {
    private lateinit var textview_appointment_title: TextView
    private lateinit var textview_appointment_purpose: TextView
    private lateinit var textview_appointment_date: TextView
    private lateinit var textview_appointment_time: TextView
    private lateinit var textview_appointment_status: TextView
    private lateinit var textview_appointment_created: TextView
    private lateinit var textview_appointment_name: TextView
    private lateinit var button_complaint_update: Button
    private lateinit var spinner_appointment_status: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment_detail)
        textview_appointment_title = findViewById(R.id.textview_appointment_title)
        textview_appointment_purpose = findViewById(R.id.textview_appointment_purpose)
        textview_appointment_date = findViewById(R.id.textview_appointment_date)
        textview_appointment_time = findViewById(R.id.textview_appointment_time)
        textview_appointment_status = findViewById(R.id.textview_appointment_status)
        textview_appointment_created = findViewById(R.id.textview_appointment_created)
        textview_appointment_name = findViewById(R.id.textview_appointment_name)
        button_complaint_update = findViewById(R.id.button_complaint_update)
        spinner_appointment_status = findViewById(R.id.spinner_appointment_status)

        val imageview_allscheduledappointment_back = findViewById<ImageView>(R.id.imageview_allscheduledappointment_back)

        var appointmentId = ""
        var title = ""
        var purpose = ""
        var date = ""
        var createdAt = ""
        var status = ""
        var time = ""
        var firstName = ""
        var middleName = ""
        var lastName = ""
        var suffix = ""

        val statusOptions = arrayOf("Pending", "Confirmed", "Cancelled")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_appointment_status.adapter = adapter

        intent?.let {
            it.getStringExtra("appointmentId")?.let { id ->
                appointmentId = id
            }
            it.getStringExtra("title")?.let { t ->
                title = t
            }
            it.getStringExtra("purpose")?.let { p ->
                purpose = p
            }
            it.getStringExtra("date")?.let { d ->
                date = d
            }
            it.getStringExtra("created")?.let { c ->
                createdAt = c
            }
            it.getStringExtra("status")?.let { s ->
                status = s
            }
            it.getStringExtra("time")?.let { timeVal ->
                time = timeVal
            }
            it.getStringExtra("firstName")?.let { fn ->
                firstName = fn
            }
            it.getStringExtra("middleName")?.let { mn ->
                middleName = mn
            }
            it.getStringExtra("lastName")?.let { ln ->
                lastName = ln
            }
            it.getStringExtra("suffix")?.let { sfx ->
                suffix = sfx
            }
        }

        textview_appointment_title.text = title
        textview_appointment_purpose.text = purpose
        textview_appointment_date.text = date
        textview_appointment_time.text = time
        textview_appointment_status.text = status
        textview_appointment_created.text = createdAt
        textview_appointment_name.text = listOf(firstName, middleName, lastName, suffix)
            .filter { it.isNotBlank() }
            .joinToString(" ")

        val statusIndex = statusOptions.indexOf(status)
        if (statusIndex >= 0) {
            spinner_appointment_status.setSelection(statusIndex)
        }

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            button_complaint_update.setOnClickListener {
                val updatedStatus = spinner_appointment_status.selectedItem.toString()
                updateStatus(appointmentId, updatedStatus, bearerToken)
            }
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }

        imageview_allscheduledappointment_back.setOnClickListener {
            startActivity(
                Intent(this, AdminAllAppointmentActivity::class.java)
            )
        }
    }
    private fun updateStatus(id: String, newStatus: String, token: String) {
        val body = mapOf("status" to newStatus)

        RetrofitClient.instance.updateAppointmentStatus(id, body, token)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AdminAppointmentDetailActivity, "Status updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AdminAppointmentDetailActivity, "Failed to update status.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AdminAppointmentDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}