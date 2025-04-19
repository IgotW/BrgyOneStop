package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.request.RegisterRequest
import com.google.brgyonestop.response.RegisterResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register3Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)

        var username = ""
        var email = ""
        var phone = ""
        var password = ""
        var firstname = ""
        var middlename = ""
        var lastname = ""
        var suffix = ""

        val purok = findViewById<EditText>(R.id.edittext_reg_purok)
        val street = findViewById<EditText>(R.id.edittext_reg_street)
        val barangay = findViewById<EditText>(R.id.edittext_reg_barangay)
        val municipality = findViewById<EditText>(R.id.edittext_reg_municipality)
        val province = findViewById<EditText>(R.id.edittext_reg_province)
        val zipcode = findViewById<EditText>(R.id.edittext_reg_zipcode)
        val button_register3_next = findViewById<Button>(R.id.button_register3_next)
        val button_register3_cancel = findViewById<Button>(R.id.button_register3_cancel)


        intent?.let {
            it.getStringExtra("username")?.let { user->
                username = user
            }
            it.getStringExtra("email")?.let{mail ->
                email = mail
            }
            it.getStringExtra("phone")?.let { phonenumber ->
                phone = phonenumber
            }
            it.getStringExtra("password")?.let { pass ->
                password = pass
            }
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
        }


        button_register3_next.setOnClickListener {

            try {
//                val body = mapOf(
//                    "username" to username,
//                    "email" to email,
//                    "phoneNumber" to phone,
//                    "password" to password,
//                    "firstName" to firstname,
//                    "middleName" to middlename,
//                    "lastName" to lastname,
//                    "suffix" to suffix,
//                    "purok" to purok.text.toString(),
//                    "street" to street.text.toString(),
//                    "barangay" to barangay.text.toString(),
//                    "municipality" to municipality.text.toString(),
//                    "province" to province.text.toString(),
//                    "zipcode" to zipcode
//                )
                val request = RegisterRequest(
                    username, email, phone, password, firstname,
                    middlename, lastname, suffix, purok.text.toString(),
                    street.text.toString(), barangay.text.toString(), municipality.text.toString(),
                    province.text.toString(), zipcode.text.toString()
                )

                // Make API call using RetrofitClient
                RetrofitClient.instance.registerUser(request).enqueue(object :
                    Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            // Handle successful response
                            runOnUiThread {
                                Toast.makeText(this@Register3Activity, "Registered Successfully", Toast.LENGTH_SHORT).show()
                                Log.d("API_SUCCESS", response.body().toString())
                                startActivity(
                                    Intent(this@Register3Activity, LoginActivity::class.java)
                                )
                            }
                        } else {
                            // Handle failed response
                            runOnUiThread {
                                Toast.makeText(this@Register3Activity, "Registration Failed", Toast.LENGTH_SHORT).show()
                                Log.e("API_ERROR", response.errorBody()?.string().toString())
                            }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // Handle failure
                        runOnUiThread {
                            Toast.makeText(this@Register3Activity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            Log.e("API_EXCEPTION", t.toString())
                        }
                    }
                })
            }catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@Register3Activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_EXCEPTION", e.toString())
                }
            }
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }




        button_register3_cancel.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }
}