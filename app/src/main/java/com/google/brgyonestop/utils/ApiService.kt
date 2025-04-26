package com.google.brgyonestop.utils

import com.google.brgyonestop.request.RegisterRequest
import com.google.brgyonestop.response.LoginResponse
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

    @GET("api/users/auth/profile")
    fun getUserProfile(@Header("Authorization") token: String
    ): Call<ProfileResponse>
}