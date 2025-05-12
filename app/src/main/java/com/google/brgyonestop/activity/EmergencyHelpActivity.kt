package com.google.brgyonestop.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.brgyonestop.HistoryActivity
import com.google.brgyonestop.R

class EmergencyHelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergencyhelp)

        val backArrowImageView: ImageView = findViewById(R.id.leftarrow)

        backArrowImageView.setOnClickListener {
            onBackPressed()
        }

        val btnCallPolice = findViewById<Button>(R.id.call_police)
        val btnCallFire = findViewById<Button>(R.id.call_fire)
        val btnCallAmbulance = findViewById<Button>(R.id.call_ambulance)
        val btnCallTanod = findViewById<Button>(R.id.call_tanod)

        btnCallPolice.setOnClickListener {
            makeCall("123") // Or use your fetched number
        }

        btnCallFire.setOnClickListener {
            makeCall("426") // Replace with actual fire department number
        }

        btnCallAmbulance.setOnClickListener {
            makeCall("759") // Replace with actual ambulance number
        }
        btnCallTanod.setOnClickListener {
            makeCall("132") // Replace with actual ambulance number
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
    }

    private fun makeCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }
}