package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val dashboard_name = findViewById<TextView>(R.id.textview_dashboard_name)
        var firstname = ""
        var middlename = ""
        var lastname = ""
        var suffix = ""

        intent?.let {
            it.getStringExtra("firstname")?.let { fname ->
                firstname = fname
            }
            it.getStringExtra("middlename")?.let { mname ->
                middlename = mname
            }
            it.getStringExtra("lastname")?.let { lname ->
                lastname = lname
            }
            it.getStringExtra("suffix")?.let { extrasuffix ->
                suffix = extrasuffix
            }

        }

        //commit and push the latest API to work the middlename part, test the dashboard must have the fullname displayed
        //fix the name and notification part it must be fixed not moving the notification button.

        val fullName = "$firstname $middlename $lastname $suffix".trim().replace(Regex(" +"), " ")

        dashboard_name.text = fullName

    }
}