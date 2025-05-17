package com.google.brgyonestop.models

data class Appointment(
    val _id: String,
    val title: String,
    val purpose: String,
    val date: String,
    val time: String,
    val status: String,
    val createdAt: String,
    val user: Users
)