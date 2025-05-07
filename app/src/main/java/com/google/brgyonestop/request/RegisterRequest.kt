package com.google.brgyonestop.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val birthdate: String,
    val gender: String,
    val purok: String,
    val street: String,
    val barangay: String,
    val municipality: String,
    val province: String,
    val zipCode: String
)
