package com.google.brgyonestop.activity

import android.app.Activity
import android.os.Bundle
import com.google.brgyonestop.R

class DashboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}