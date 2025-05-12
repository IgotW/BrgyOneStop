package com.google.brgyonestop.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)