package com.google.brgyonestop.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.request.AppointmentRequest
import com.google.brgyonestop.response.AppointmentResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class ScheduleAppointmentFormActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_appointment_form)

        val scheduleappointment_title = findViewById<EditText>(R.id.scheduleappointment_title)
        val scheduleappointment_time = findViewById<EditText>(R.id.scheduleappointment_time)
        val scheduleappointment_purpose = findViewById<EditText>(R.id.scheduleappointment_purpose)
        val scheduleappointment_button = findViewById<Button>(R.id.scheduleappointment_button)
        val dateEditText = findViewById<EditText>(R.id.scheduleappointment_date)

        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedMonth = String.format("%02d", selectedMonth + 1)
                    val formattedDay = String.format("%02d", selectedDay)
                    val formattedDate = "$formattedMonth/$formattedDay/$selectedYear"
                    dateEditText.setText(formattedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        scheduleappointment_button.setOnClickListener {
            val date = dateEditText.text.toString()
            val time = scheduleappointment_time.text.toString()
            val title = scheduleappointment_title.text.toString()
            val purpose = scheduleappointment_purpose.text.toString()

            if (date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Date and Time are required", Toast.LENGTH_SHORT).show()
            } else {
                bookAppointment(title, purpose, date, time)
            }
        }

    }
    private fun bookAppointment(title: String, purpose: String, date: String, time: String) {
        val token = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("token", null)

        if (token != null) {
            val request = AppointmentRequest(title, purpose, date, time)

            RetrofitClient.instance.scheduleAppointment("Bearer $token", request)
                .enqueue(object : Callback<AppointmentResponse> {
                    override fun onResponse(
                        call: Call<AppointmentResponse>,
                        response: Response<AppointmentResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@ScheduleAppointmentFormActivity, "Appointment booked!", Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(this@ScheduleAppointmentFormActivity, ScheduleAppointmentActivity::class.java)
                            )
                        } else {
                            Toast.makeText(this@ScheduleAppointmentFormActivity, "Failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                        Toast.makeText(this@ScheduleAppointmentFormActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}