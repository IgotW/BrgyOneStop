package com.google.brgyonestop.response

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)