package com.google.brgyonestop.request

data class AnnouncementRequest(
    val _id: String,
    val title: String,
    val description: String,
    val createdAt: String
)