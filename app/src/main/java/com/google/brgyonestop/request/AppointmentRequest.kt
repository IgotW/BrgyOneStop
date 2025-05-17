package com.google.brgyonestop.request

data class AppointmentRequest(
    val title: String,
    val purpose: String,
    val date: String,
    val time: String
)