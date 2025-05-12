package com.google.brgyonestop.request

data class UpdateProfileRequest(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val phone: String,
    val birthdate: String,
    val gender: String
)