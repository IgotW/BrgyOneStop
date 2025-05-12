package com.google.brgyonestop.response

import com.google.brgyonestop.models.Users

data class UsersResponse(
    val success: Boolean,
    val message: String,
    val users: List<Users>
)