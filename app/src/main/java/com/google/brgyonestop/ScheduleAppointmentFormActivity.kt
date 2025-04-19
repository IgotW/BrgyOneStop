package com.google.brgyonestop

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import java.util.Calendar

class ScheduleAppointmentFormActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment_form)

        val dateEditText = findViewById<EditText>(R.id.dateEditText)

        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear,selectedMonth,selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                dateEditText.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

    }
}