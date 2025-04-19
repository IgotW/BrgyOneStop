package com.google.brgyonestop.models

data class Users(
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val purok: String,
    val street: String,
    val barangay: String,
    val municipality: String,
    val province: String,
    val zipCode: String
)