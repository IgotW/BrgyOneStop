package com.google.brgyonestop.response

import com.google.brgyonestop.models.Complaint

data class ComplaintResponse(
    val success: Boolean,
    val complaints: List<Complaint>
)