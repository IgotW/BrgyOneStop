package com.google.brgyonestop.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.request.UpdateProfileRequest
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class UserEditProfileActivity : Activity() {
    private lateinit var editprofile_firstname: EditText
    private lateinit var editprofile_middlename: EditText
    private lateinit var editprofile_lastname: EditText
    private lateinit var editprofile_suffix: EditText
    private lateinit var editprofile_birthdate: EditText
    private lateinit var editprofile_gender: EditText
    private lateinit var button_edit_profile: Button
    private lateinit var editprofile_username: TextView
    private lateinit var editprofile_purok: TextView
    private lateinit var editprofile_street: TextView
    private lateinit var editprofile_barangay: TextView
    private lateinit var editprofile_municipal: TextView
    private lateinit var editprofile_province: TextView
    private lateinit var editprofile_zip: TextView
    private lateinit var editprofile_email: TextView
    private lateinit var editprofile_phone: EditText

//    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit_profile)

        editprofile_firstname = findViewById(R.id.edittext_editprofile_firstname)
        editprofile_middlename = findViewById(R.id.edittext_editprofile_middlename)
        editprofile_lastname = findViewById(R.id.edittext_editprofile_lastname)
        editprofile_suffix = findViewById(R.id.edittext_editprofile_suffix)
        editprofile_birthdate = findViewById(R.id.edittext_editprofile_birthdate)
        editprofile_gender = findViewById(R.id.edittext_editprofile_gender)
        button_edit_profile = findViewById(R.id.button_editprofile_save)
        editprofile_username = findViewById(R.id.textview_editprofile_username)
        editprofile_purok = findViewById(R.id.textview_editprofile_purok)
        editprofile_street = findViewById(R.id.textview_editprofile_street)
        editprofile_barangay = findViewById(R.id.textview_editprofile_barangay)
        editprofile_municipal = findViewById(R.id.textview_editprofile_municipality)
        editprofile_province = findViewById(R.id.textview_editprofile_province)
        editprofile_zip = findViewById(R.id.textview_editprofile_zip)
        editprofile_email = findViewById(R.id.textview_editprofile_email)
        editprofile_phone = findViewById(R.id.edittext_editprofile_phone)

        val imageview_editprofile_back = findViewById<ImageView>(R.id.imageview_editprofile_back)

        val imageview_editprofile_calendar = findViewById<ImageView>(R.id.imageview_editprofile_calendar)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token != null){
            fetchProfile("Bearer $token")

            imageview_editprofile_calendar.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val formattedMonth = String.format("%02d", selectedMonth + 1)
                        val formattedDay = String.format("%02d", selectedDay)
                        val formattedDate = "$formattedMonth/$formattedDay/$selectedYear"
                        editprofile_birthdate.setText(formattedDate)
                    }, year, month, day)
                datePickerDialog.show()
            }

            button_edit_profile.setOnClickListener {
                var hasError = false

                if(editprofile_firstname.text.isNullOrEmpty()){
                    editprofile_firstname.error = "First Name is required"
                    hasError = true
                    Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show()
                }
                if(editprofile_lastname.text.isNullOrEmpty()){
                    editprofile_lastname.error = "Last Name is required"
                    hasError = true
                    Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show()
                }
                if(editprofile_phone.text.isNullOrEmpty()){
                    editprofile_phone.error = "Phone Number is required"
                    hasError = true
                    Toast.makeText(this, "Phone Number is required", Toast.LENGTH_SHORT).show()
                }
                if(editprofile_birthdate.text.isNullOrEmpty()){
                    editprofile_birthdate.error = "Birthdate is required"
                    hasError = true
                    Toast.makeText(this, "Birthdate is required", Toast.LENGTH_SHORT).show()
                }
                if(editprofile_gender.text.isNullOrEmpty()){
                    editprofile_gender.error = "Gender is required"
                    hasError = true
                    Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show()
                }

                if (hasError) return@setOnClickListener

                val request = UpdateProfileRequest(
                    firstName = editprofile_firstname.text.toString(),
                    middleName = editprofile_middlename.text.toString(),
                    lastName = editprofile_lastname.text.toString(),
                    suffix = editprofile_suffix.text.toString(),
                    phone = editprofile_phone.text.toString(),
                    birthdate = editprofile_birthdate.text.toString(),
                    gender = editprofile_gender.text.toString()
                )
                updateProfile("Bearer $token", request)

            }
        }
        imageview_editprofile_back.setOnClickListener{
            startActivity(
                Intent(this, UserProfileActivity::class.java)
            )
        }
    }
    private fun fetchProfile(token: String) {
        RetrofitClient.instance.getUserProfile(token).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!.user
                    editprofile_firstname.setText(user.firstName)
                    editprofile_middlename.setText(user.middleName)
                    editprofile_lastname.setText(user.lastName)
                    editprofile_suffix.setText(user.suffix)
                    editprofile_gender.setText(user.gender)
                    editprofile_username.text = user.username
                    editprofile_email.text = user.email
                    editprofile_phone.setText(user.phoneNumber)
                    editprofile_purok.text = user.purok
                    editprofile_street.text = user.street
                    editprofile_barangay.text = user.barangay
                    editprofile_municipal.text = user.municipality
                    editprofile_province.text = user.province
                    editprofile_zip.text = user.zipCode

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val birthdateString = dateFormat.format(user.birthdate)
                    editprofile_birthdate.setText(birthdateString)

                } else {
                    Log.e("PROFILE_ERROR", "Failed to load profile: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("PROFILE_EXCEPTION", t.message.toString())
            }
        })
    }

    private fun updateProfile(token: String, request: UpdateProfileRequest) {
        RetrofitClient.instance.updateUserProfile(token, request)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UserEditProfileActivity, "Profile updated!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@UserEditProfileActivity, "Update failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(this@UserEditProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}