package com.google.brgyonestop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class ScheduleAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)

        val scheduleappointment_button = findViewById<Button>(R.id.appointment_button)
        val allscheduleappointment_button = findViewById<Button>(R.id.allscheduleappointment_button)
        val imageview_schedule_back = findViewById<ImageView>(R.id.imageview_schedule_back)

        scheduleappointment_button.setOnClickListener {
            startActivity(
                Intent(this, ScheduleAppointmentFormActivity::class.java)
            )
        }
        imageview_schedule_back.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }
        allscheduleappointment_button.setOnClickListener {
            startActivity(
                Intent(this, UserAllAppointmentsActivity::class.java)
            )
        }
    }
}