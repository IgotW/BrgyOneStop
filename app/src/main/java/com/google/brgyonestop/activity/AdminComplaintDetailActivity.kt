package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide

class AdminComplaintDetailActivity : Activity() {
    private lateinit var statusSpinner: Spinner
    private lateinit var button_complaint_update: Button
    private lateinit var imageview_complaint_file: ImageView
    private lateinit var complaintId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_complaint_detail)

        val textview_complaint_category = findViewById<TextView>(R.id.textview_complaint_category)
        val textview_complaint_details = findViewById<TextView>(R.id.textview_complaint_details)
        val textview_complaint_status = findViewById<TextView>(R.id.textview_complaint_status)
        val textview_complaint_createdat = findViewById<TextView>(R.id.textview_complaint_createdat)
        val textview_complaint_name = findViewById<TextView>(R.id.textview_complaint_name)
        val imageview_allcomplaints_back = findViewById<ImageView>(R.id.imageview_allcomplaints_back)
        imageview_complaint_file = findViewById(R.id.imageview_complaint_file)
        statusSpinner = findViewById(R.id.spinner_complaint_status)
        button_complaint_update = findViewById(R.id.button_complaint_update)
        var category = ""
        var details = ""
        var file = ""
        var createdAt = ""
        var status = ""
        var statusUpdate = ""
        var isAnonymous = false
        var username = ""

        val statusOptions = arrayOf("Pending", "In Progress", "Resolved", "Rejected")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusSpinner.adapter = adapter

        intent?.let {
            it.getStringExtra("complaintId")?.let { id ->
                complaintId = id
            }
            it.getStringExtra("category")?.let { cate ->
                category = cate
            }
            it.getStringExtra("details")?.let { det ->
                details = det
            }
            it.getStringExtra("file")?.let { photo ->
                file = photo
            }
            it.getStringExtra("created")?.let { create ->
                createdAt = create
            }
            it.getStringExtra("status")?.let { stat ->
                status = stat
            }
            it.getStringExtra("status-update")?.let { update ->
                statusUpdate = update
            }
            it.getBooleanExtra("isAnonymous", false).let { anonymous ->
                isAnonymous = anonymous
            }
            it.getStringExtra("username")?.let { name ->
                username = name
            }
        }

        textview_complaint_category.text = "Category: $category"
        textview_complaint_details.text = "Details: $details"
        textview_complaint_status.text = "Status: $status"
        textview_complaint_createdat.text = "Created At: $createdAt"

        textview_complaint_name.text = if (isAnonymous) {
            "Submitted by: Anonymous"
        } else {
            "Submitted by: $username"
        }


        if (!file.isNullOrEmpty()) {
            Log.d("ComplaintDebug", "Loading image from: $file")
            Glide.with(this)
                .load(file)
                .error(R.drawable.img_nocontent)
                .into(imageview_complaint_file)
        } else {
            Log.d("ComplaintDebug", "File is null or empty")
        }

        val statusIndex = statusOptions.indexOf(status)
        if (statusIndex >= 0) {
            statusSpinner.setSelection(statusIndex)
        }

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            button_complaint_update.setOnClickListener {
                val updatedStatus = statusSpinner.selectedItem.toString()
                updateStatus(complaintId, updatedStatus, bearerToken)
            }
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }

        imageview_allcomplaints_back.setOnClickListener{
            startActivity(
                Intent(this, AdminAllFiledComplaintsActivity::class.java)
            )
        }

    }
    private fun updateStatus(id: String, newStatus: String, token: String) {
        val body = mapOf("status" to newStatus)

        RetrofitClient.instance.updateComplaintStatus(id, body, token)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AdminComplaintDetailActivity, "Status updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AdminComplaintDetailActivity, "Failed to update status.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AdminComplaintDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}