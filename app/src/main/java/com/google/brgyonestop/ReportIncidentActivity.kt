package com.google.brgyonestop

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.testingapplication.EmergencyHelpActivity
import com.google.brgyonestop.activity.DashboardActivity
import java.util.Locale

class ReportIncidentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_incident)

        val backArrowImageView: ImageView = findViewById(R.id.leftarrow)

        backArrowImageView.setOnClickListener {
            onBackPressed()
        }
        val home = findViewById<LinearLayout>(R.id.home_nav)
        home.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val inbox = findViewById<LinearLayout>(R.id.inbox_nav)
        inbox.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val call = findViewById<ImageView>(R.id.emergencyButton)
        call.setOnClickListener{
            val intent = Intent(this, EmergencyHelpActivity::class.java)
            startActivity(intent)
        }
        val history = findViewById<LinearLayout>(R.id.history_nav)
        history.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        val profile = findViewById<LinearLayout>(R.id.profile_nav)
        home.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val dateText = findViewById<EditText>(R.id.show_datetext)
        val datePickerIcon = findViewById<ImageView>(R.id.date_picker)
        datePickerIcon.setOnClickListener {
            val currentDate = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    dateText.setText(sdf.format(selectedDate.time))
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Time Picker
        val timeText = findViewById<EditText>(R.id.show_timetext)
        val timePickerIcon = findViewById<ImageView>(R.id.time_picker)
        timePickerIcon.setOnClickListener {
            val currentTime = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    timeText.setText(sdf.format(selectedTime.time))
                },
                currentTime.get(Calendar.HOUR_OF_DAY),
                currentTime.get(Calendar.MINUTE),
                false // Use 12-hour format
            ).show()
        }
    }
}