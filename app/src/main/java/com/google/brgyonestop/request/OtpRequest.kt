package com.google.brgyonestop.request

data class OtpRequest(
    val email: String,
    val otp: String
)