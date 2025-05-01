package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.request.OtpRequest
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.response.OtpResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword2Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        val imageview_f2backarrow = findViewById<ImageView>(R.id.imageview_f2backarrow)
        val edittext_forgot2_code1 = findViewById<EditText>(R.id.edittext_forgot2_code1)
        val edittext_forgot2_code2 = findViewById<EditText>(R.id.edittext_forgot2_code2)
        val edittext_forgot2_code3 = findViewById<EditText>(R.id.edittext_forgot2_code3)
        val edittext_forgot2_code4 = findViewById<EditText>(R.id.edittext_forgot2_code4)
        val button_sendcode = findViewById<Button>(R.id.button_sendcode)
        var email = ""

        intent?.let {
            it.getStringExtra("email")?.let {mail ->
                email = mail
            }
        }

        button_sendcode.setOnClickListener {
            val otp = edittext_forgot2_code1.text.toString() +
                    edittext_forgot2_code2.text.toString() +
                    edittext_forgot2_code3.text.toString() +
                    edittext_forgot2_code4.text.toString()

            if (otp.length != 4 || email.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter the 6-digit code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = OtpRequest(email, otp)

            RetrofitClient.instance.verifyOtp(request).enqueue(object : Callback<OtpResponse> {
                override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val userId = response.body()!!.userId
                        Log.d("OTP", "OTP verified. User ID: $userId")

                        startActivity(
                            Intent(this@ForgotPassword2Activity, ForgotPassword3Activity::class.java).apply {
                                putExtra("userId", userId)
                            }
                        )

                    } else {
                        Log.e("OTP", "Verification failed: ${response.body()?.message}")
                        Toast.makeText(this@ForgotPassword2Activity, "Invalid or expired code", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                    Log.e("OTP", "Network error: ${t.message}")
                    Toast.makeText(this@ForgotPassword2Activity, "Failed to verify code: ${t.message}", Toast.LENGTH_SHORT).show()

                }
            })

        }

        imageview_f2backarrow.setOnClickListener {
            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )
        }

    }
}