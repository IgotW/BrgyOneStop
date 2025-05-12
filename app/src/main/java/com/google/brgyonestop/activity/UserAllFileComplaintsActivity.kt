package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.adapter.ComplaintAdapter
import com.google.brgyonestop.response.ComplaintResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAllFileComplaintsActivity : Activity() {
    private lateinit var listview_complaint_all: ListView
    private lateinit var complaintAdapter: ComplaintAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_all_file_complaints)

        val imageview_allfilecomplaint_back = findViewById<ImageView>(R.id.imageview_allfilecomplaint_back)
        val button_complaint_create = findViewById<Button>(R.id.button_complaint_create)
        listview_complaint_all = findViewById(R.id.listview_complaint_all)

        imageview_allfilecomplaint_back.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }
        button_complaint_create.setOnClickListener {
            startActivity(
                Intent(this, FileComplaintActivity::class.java)
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
        val call = RetrofitClient.instance.getUserComplaints(token)

        call.enqueue(object : Callback<ComplaintResponse> {
            override fun onResponse(
                call: Call<ComplaintResponse>,
                response: Response<ComplaintResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // Get the list of complaints from the response
                    val complaints = response.body()!!.complaints

                    // Set up the adapter and bind it to the ListView
                    complaintAdapter = ComplaintAdapter(this@UserAllFileComplaintsActivity, complaints)
                    listview_complaint_all.adapter = complaintAdapter

                } else {
                    Toast.makeText(this@UserAllFileComplaintsActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ComplaintResponse>, t: Throwable) {
                Toast.makeText(this@UserAllFileComplaintsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}