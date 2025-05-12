package com.google.brgyonestop.utils

import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.request.AnnouncementRequest
import com.google.brgyonestop.request.ChangePasswordRequest
import com.google.brgyonestop.request.EmailRequest
import com.google.brgyonestop.request.OtpRequest
import com.google.brgyonestop.request.RegisterRequest
import com.google.brgyonestop.request.ResetPasswordRequest
import com.google.brgyonestop.request.UpdateProfileRequest
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.response.AppointmentsCountResponse
import com.google.brgyonestop.response.ComplaintResponse
import com.google.brgyonestop.response.ComplaintsCountResponse
import com.google.brgyonestop.response.LoginResponse
import com.google.brgyonestop.response.OtpResponse
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.response.RegisterResponse
import com.google.brgyonestop.response.UserCountResponse
import com.google.brgyonestop.response.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Headers("Content-Type: application/json")

    @POST("/api/users/auth/login")
    fun loginUser(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("api/admin/login")
    fun loginAdmin(@Body body: Map<String, String>): Call<LoginResponse>

    @POST("/api/users/auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("api/admin/create-announcement")
    fun createAnnouncement(
        @Header("Authorization") token: String,
        @Body request: AnnouncementRequest
    ): Call<AnnouncementResponse>

    @POST("/api/users/auth/forgot-password")
    fun sendOtp(@Body request: EmailRequest): Call<ApiResponse>

    @POST("/api/users/auth/verify-otp")
    fun verifyOtp(@Body request: OtpRequest): Call<OtpResponse>

    @POST("/api/users/auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ApiResponse>

    @Multipart
    @POST("/api/users/auth/file-complaint")
    fun fileComplaint(
        @Header("Authorization") token: String,
        @Part("category") category: RequestBody,
        @Part("details") details: RequestBody,
        @Part("anonymous") anonymous: RequestBody,
        @Part file: MultipartBody.Part? = null
    ): Call<ResponseBody>

    //post /schedule-appointment



    @GET("api/users/auth/profile")
    fun getUserProfile(@Header("Authorization") token: String
    ): Call<ProfileResponse>

    @GET("/api/announcement/announcements")
    fun getAnnouncements(@Header("Authorization") token: String
    ): Call<AnnouncementResponse>

    //get allusers

    @GET("/api/admin/count-users")
    fun getUserCount(
        @Header("Authorization") token: String
    ): Call<UserCountResponse>

    //get /complaints
    @GET("/api/admin/complaints")
    fun getComplaints(
        @Header("Authorization") token: String
    ): Call<ComplaintResponse>

    //get /count-complaints
    @GET("/api/admin/count-complaints")
    fun getComplaintsCount(
        @Header("Authorization") token: String
    ): Call<ComplaintsCountResponse>

    //get /appointments

    //get /count-appointments
    @GET("/api/admin/count-appointments")
    fun getAppointmentsCount(
        @Header("Authorization") token: String
    ): Call<AppointmentsCountResponse>

    @GET("/api/users/auth/complaints")
    fun getUserComplaints(
        @Header("Authorization") token: String
    ): Call<ComplaintResponse>

    @GET("/api/admin/users")
    fun getAllUsers(
        @Header("Authorization") token: String
    ): Call<UsersResponse>


    //put /complaint-status/:id
    @PUT("/api/admin/complaint-status/{id}")
    fun updateComplaintStatus(
        @Path("id") id: String,
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Call<Void>

    //put /appointment-status/:id

    @PUT("/api/announcement/edit-announcement/{id}")
    fun updateAnnouncement(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateData: Map<String, String>
    ): Call<AnnouncementResponse>

    @PUT("/api/users/auth/update-profile")
    fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body updatedData: UpdateProfileRequest
    ): Call<ProfileResponse>

    @PUT("/user/change-password")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Call<ApiResponse>

    @DELETE("/api/announcement/announcements/{id}")
    fun deleteAnnouncement(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Void>
}