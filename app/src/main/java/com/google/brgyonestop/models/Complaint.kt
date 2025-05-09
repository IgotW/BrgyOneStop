package com.google.brgyonestop.models

data class Complaint(
    val _id: String,
    val category: String,
    val details: String,
    val anonymous: Boolean,
    val file: String?,
    val status: String,
    val statusUpdatedAt: String,
    val createdAt: String,
    val user: Users
)