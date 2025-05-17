package com.google.brgyonestop.models

data class Appointments(
    val _id: String,
    val title: String,
    val user: String,
    val purpose: String,
    val date: String,
    val time: String,
    val status: String,
    val createdAt: String,
)