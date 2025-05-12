package com.google.brgyonestop.models

import java.util.Date

data class Users(
    val username: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val birthdate: Date,
    val gender: String,
    val purok: String,
    val street: String,
    val barangay: String,
    val municipality: String,
    val province: String,
    val zipCode: String,
    val role: String
)