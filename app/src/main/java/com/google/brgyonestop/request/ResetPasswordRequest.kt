package com.google.brgyonestop.request

data class ResetPasswordRequest(
    val userId: String,
    val newPassword: String
)