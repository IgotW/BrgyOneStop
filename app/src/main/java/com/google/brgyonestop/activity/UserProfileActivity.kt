package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.HistoryActivity
import com.google.brgyonestop.R
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class UserProfileActivity : Activity() {

    private lateinit var textview_profile_name: TextView
    private lateinit var textview_profile_email: TextView
    private lateinit var textview_profile_phone: TextView
    private lateinit var textview_profile_username: TextView
    private lateinit var textview_profile_birthdate: TextView
    private lateinit var textview_profile_gender: TextView
    private lateinit var textview_profile_purok: TextView
    private lateinit var textview_profile_street: TextView
    private lateinit var textview_profile_barangay: TextView
    private lateinit var textview_profile_municipal: TextView
    private lateinit var textview_profile_province: TextView
    private lateinit var textview_profile_zip: TextView
    private lateinit var button_profile_edit: Button
    private lateinit var button_profile_logout: Button
    private lateinit var button_profile_changepass: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        textview_profile_name = findViewById(R.id.textview_profile_name)
        textview_profile_email = findViewById(R.id.textview_profile_email)
        textview_profile_phone = findViewById(R.id.textview_profile_phone)
        textview_profile_username = findViewById(R.id.textview_profile_username)
        textview_profile_birthdate = findViewById(R.id.textview_profile_birthdate)
        textview_profile_gender = findViewById(R.id.textview_profile_gender)
        textview_profile_purok = findViewById(R.id.textview_profile_purok)
        textview_profile_street = findViewById(R.id.textview_profile_street)
        textview_profile_barangay = findViewById(R.id.textview_profile_barangay)
        textview_profile_municipal = findViewById(R.id.textview_profile_municipality)
        textview_profile_province = findViewById(R.id.textview_profile_province)
        textview_profile_zip = findViewById(R.id.textview_profile_zip)
        button_profile_edit = findViewById(R.id.button_profile_edit)
        button_profile_logout = findViewById(R.id.button_profile_logout)
        button_profile_changepass = findViewById(R.id.button_profile_changepass)

        val home = findViewById<LinearLayout>(R.id.home_nav)
        home.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val inbox = findViewById<LinearLayout>(R.id.inbox_nav)
        inbox.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        val call = findViewById<ImageView>(R.id.emergencyButton)
        call.setOnClickListener{
            val intent = Intent(this, EmergencyHelpActivity::class.java)
            startActivity(intent)
        }
        val history = findViewById<LinearLayout>(R.id.history_nav)
        history.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        val profile = findViewById<LinearLayout>(R.id.profile_nav)
        profile.setOnClickListener{
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
        button_profile_changepass.setOnClickListener {
            startActivity(
                Intent(this, ChangePasswordActivity::class.java)
            )
        }
    }
    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            fetchprofile(bearerToken)

            button_profile_edit.setOnClickListener {
                startActivity(
                    Intent(this, UserEditProfileActivity::class.java)
                )
            }
            button_profile_logout.setOnClickListener {
                startActivity(
                    Intent(this, LoginActivity::class.java)
                )
            }
        }
    }
    private fun fetchprofile(token: String){
        RetrofitClient.instance.getUserProfile(token).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val profileList = response.body()!!.user

                    textview_profile_name.setText(
                        "${profileList.firstName} ${profileList.middleName} ${profileList.lastName} ${profileList.suffix}"
                    )
                    textview_profile_email.setText(profileList.email)
                    textview_profile_phone.setText(profileList.phoneNumber)
                    textview_profile_username.setText(profileList.username)
                    textview_profile_gender.setText(profileList.gender)
                    textview_profile_purok.setText(profileList.purok)
                    textview_profile_street.setText(profileList.street)
                    textview_profile_barangay.setText(profileList.barangay)
                    textview_profile_municipal.setText(profileList.municipality)
                    textview_profile_province.setText(profileList.province)
                    textview_profile_zip.setText(profileList.zipCode)

                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val birthdateString = dateFormat.format(profileList.birthdate)
                    textview_profile_birthdate.setText(birthdateString)


                } else {
                    Log.e("PROFILE_ERROR", "Failed to load profile: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("PROFILE_EXCEPTION", t.message.toString())
            }
        })
    }
}