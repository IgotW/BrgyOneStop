package com.google.brgyonestop.request

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)