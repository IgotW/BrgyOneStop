package com.google.brgyonestop.utils

import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.request.EmailRequest
import com.google.brgyonestop.request.OtpRequest
import com.google.brgyonestop.request.RegisterRequest
import com.google.brgyonestop.request.ResetPasswordRequest
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.response.LoginResponse
import com.google.brgyonestop.response.OtpResponse
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")

    @POST("/api/users/auth/login")
    fun loginUser(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("api/admin/login")
    fun loginAdmin(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("/api/users/auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/admin/create-announcement")
    fun createAnnouncement(
        @Header("Authorization") token: String,
        @Body announcement: Announcement
    ): Call<Void>

    @POST("/api/users/auth/forgot-password")
    fun sendOtp(@Body request: EmailRequest): Call<ApiResponse>

    @POST("/api/users/auth/verify-otp")
    fun verifyOtp(@Body request: OtpRequest): Call<OtpResponse>

    @POST("/api/users/auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ApiResponse>

    @GET("api/users/auth/profile")
    fun getUserProfile(@Header("Authorization") token: String
    ): Call<ProfileResponse>

    @GET("/api/announcement/announcements")
    fun getAnnouncements(@Header("Authorization") token: String): Call<AnnouncementResponse>
}