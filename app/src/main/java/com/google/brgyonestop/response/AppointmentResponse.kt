package com.google.brgyonestop.response

import com.google.brgyonestop.models.Appointment

data class AppointmentResponse(
    val success: Boolean,
    val message: String,
    val appointments: List<Appointment>
)