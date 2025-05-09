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
import com.google.brgyonestop.adapter.ComplaintAdapter
import com.google.brgyonestop.models.Complaint
import com.google.brgyonestop.response.ComplaintResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAllFiledComplaintsActivity : Activity() {
    private lateinit var listview_allcomplaints: ListView
    private lateinit var complaintAdapter: ComplaintAdapter
    private val complaintsList = mutableListOf<Complaint>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_filed_complaints)

        val imageview_allcomplaints_back = findViewById<ImageView>(R.id.imageview_allcomplaints_back)
        listview_allcomplaints = findViewById(R.id.listview_allcomplaints)

        imageview_allcomplaints_back.setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java)
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
        val call = RetrofitClient.instance.getComplaints(token)

        call.enqueue(object : Callback<ComplaintResponse> {
            override fun onResponse(
                call: Call<ComplaintResponse>,
                response: Response<ComplaintResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    // Get the list of complaints from the response
                    val complaints = response.body()!!.complaints

                    // Set up the adapter and bind it to the ListView
                    complaintAdapter = ComplaintAdapter(this@AdminAllFiledComplaintsActivity, complaints)
                    listview_allcomplaints.adapter = complaintAdapter

                    listview_allcomplaints.setOnItemClickListener { _, _, position, _ ->
                        val selectedComplaint = complaints[position]

                        startActivity(
                            Intent(this@AdminAllFiledComplaintsActivity, AdminComplaintDetailActivity::class.java).apply {
                                putExtra("complaintId", selectedComplaint._id)
                                putExtra("category", selectedComplaint.category)
                                putExtra("details", selectedComplaint.details)
                                putExtra("file", selectedComplaint.file)
                                putExtra("created", selectedComplaint.createdAt)
                                putExtra("status", selectedComplaint.status)
                                putExtra("status-update", selectedComplaint.statusUpdatedAt)
                                putExtra("isAnonymous", selectedComplaint.anonymous)
                                putExtra("username", selectedComplaint.user.username)
                            }
                        )
                    }
                } else {
                    Toast.makeText(this@AdminAllFiledComplaintsActivity, "Failed to fetch complaints", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ComplaintResponse>, t: Throwable) {
                Toast.makeText(this@AdminAllFiledComplaintsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}