package com.google.brgyonestop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R

class ScheduleAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)

        val scheduleappointment_button = findViewById<Button>(R.id.scheduleappointment_button)

        scheduleappointment_button.setOnClickListener {
            startActivity(
                Intent(this, ScheduleAppointmentFormActivity::class.java)
            )
        }
    }
}