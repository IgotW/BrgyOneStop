package com.google.brgyonestop.response

import com.google.brgyonestop.request.AnnouncementRequest

data class AnnouncementResponse(
    val success: Boolean,
    val announcements: List<AnnouncementRequest>
)