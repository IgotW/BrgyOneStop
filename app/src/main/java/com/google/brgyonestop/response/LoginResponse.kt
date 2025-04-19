package com.google.brgyonestop.response

import com.google.brgyonestop.models.Users

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val user: Users
)