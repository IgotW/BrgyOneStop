package com.google.brgyonestop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ScheduleAppointmentActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment)

        val schedule = findViewById<Button>(R.id.schedule_button)
        schedule.setOnClickListener{
            val intent = Intent(this, ScheduleAppointmentFormActivity::class.java)
            startActivity(intent)
        }

    }
}