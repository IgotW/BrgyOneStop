package com.google.brgyonestop.response

import com.google.brgyonestop.models.Users

data class ProfileResponse(
    val success: Boolean,
    val message: String,
    val user: Users
)