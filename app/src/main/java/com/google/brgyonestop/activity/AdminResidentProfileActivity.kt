package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.response.ProfileResponse
import com.google.brgyonestop.response.UsersResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class AdminResidentProfileActivity : Activity() {
    private lateinit var textview_residentprofile_firstname: TextView
    private lateinit var textview_residentprofile_middlename: TextView
    private lateinit var textview_residentprofile_lastname: TextView
    private lateinit var textview_residentprofile_suffix: TextView
    private lateinit var textview_residentprofile_birthdate: TextView
    private lateinit var textview_residentprofile_gender: TextView
    private lateinit var textview_residentprofile_phone: TextView
    private lateinit var textview_residentprofile_email: TextView
    private lateinit var textview_residentprofile_purok: TextView
    private lateinit var textview_residentprofile_street: TextView
    private lateinit var textview_residentprofile_barangay: TextView
    private lateinit var textview_residentprofile_municipality: TextView
    private lateinit var textview_residentprofile_province: TextView
    private lateinit var textview_residentprofile_zip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_resident_profile)

        val imageview_residentprofile_back = findViewById<ImageView>(R.id.imageview_residentprofile_back)
        textview_residentprofile_firstname = findViewById(R.id.textview_residentprofile_firstname)
        textview_residentprofile_middlename = findViewById(R.id.textview_residentprofile_middlename)
        textview_residentprofile_lastname = findViewById(R.id.textview_residentprofile_lastname)
        textview_residentprofile_suffix = findViewById(R.id.textview_residentprofile_suffix)
        textview_residentprofile_birthdate = findViewById(R.id.textview_residentprofile_birthdate)
        textview_residentprofile_gender = findViewById(R.id.textview_residentprofile_gender)
        textview_residentprofile_phone = findViewById(R.id.textview_residentprofile_phone)
        textview_residentprofile_email = findViewById(R.id.textview_residentprofile_email)
        textview_residentprofile_purok = findViewById(R.id.textview_residentprofile_purok)
        textview_residentprofile_street = findViewById(R.id.textview_residentprofile_street)
        textview_residentprofile_barangay = findViewById(R.id.textview_residentprofile_barangay)
        textview_residentprofile_municipality = findViewById(R.id.textview_residentprofile_municipality)
        textview_residentprofile_province = findViewById(R.id.textview_residentprofile_province)
        textview_residentprofile_zip = findViewById(R.id.textview_residentprofile_zip)

        var firstname = ""
        var middlename = ""
        var lastname = ""
        var suffix = ""
        var birthdate = ""
        var gender = ""
        var phoneNumber = ""
        var email = ""
        var purok = ""
        var street = ""
        var barangay = ""
        var municipality = ""
        var province = ""
        var zipcode = ""

        intent?.let {
            it.getStringExtra("firstname")?.let { fname ->
                firstname = fname
            }
            it.getStringExtra("middlename")?.let { mname ->
                middlename = mname
            }
            it.getStringExtra("lastname")?.let { lname ->
                lastname = lname
            }
            it.getStringExtra("suffix")?.let { suf ->
                suffix = suf
            }
            it.getStringExtra("birthdate")?.let { birth ->
                birthdate = birth
            }
            it.getStringExtra("gender")?.let { gen ->
                gender = gen
            }
            it.getStringExtra("phone")?.let { phone ->
                phoneNumber = phone
            }
            it.getStringExtra("email")?.let { em ->
                email = em
            }
            it.getStringExtra("purok")?.let { pur ->
                purok = pur
            }
            it.getStringExtra("street")?.let { st ->
                street = st
            }
            it.getStringExtra("barangay")?.let { bar ->
                barangay = bar
            }
            it.getStringExtra("municipal")?.let { mun ->
                municipality = mun
            }
            it.getStringExtra("province")?.let { prov ->
                province = prov
            }
            it.getStringExtra("zip")?.let { zip ->
                zipcode = zip
            }
        }

        textview_residentprofile_firstname.setText(firstname)
        textview_residentprofile_middlename.setText(middlename)
        textview_residentprofile_lastname.setText(lastname)
        textview_residentprofile_suffix.setText(suffix)
        textview_residentprofile_birthdate.setText(birthdate)
        textview_residentprofile_gender.setText(gender)
        textview_residentprofile_phone.setText(phoneNumber)
        textview_residentprofile_email.setText(email)
        textview_residentprofile_purok.setText(purok)
        textview_residentprofile_street.setText(street)
        textview_residentprofile_barangay.setText(barangay)
        textview_residentprofile_municipality.setText(municipality)
        textview_residentprofile_province.setText(province)
        textview_residentprofile_zip.setText(zipcode)

        imageview_residentprofile_back.setOnClickListener {
            startActivity(
                Intent(this, AdminResidentsListActivity::class.java)
            )
        }

    }
}