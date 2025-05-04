package com.google.brgyonestop.response

import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.request.AnnouncementRequest

data class AnnouncementResponse(
    val success: Boolean,
    val message: String,
    val announcements: List<Announcement>
)