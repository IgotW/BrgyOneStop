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
import com.google.brgyonestop.request.EmailRequest
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val image_f1backarrow = findViewById<ImageView>(R.id.imageview_f1backarrow)
        val edittext_forgot1_email = findViewById<EditText>(R.id.edittext_forgot1_email)
        val button_resetpassword = findViewById<Button>(R.id.button_resetpassword)

        image_f1backarrow.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }

        button_resetpassword.setOnClickListener {
            val email = edittext_forgot1_email.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                val request = EmailRequest(email)
                RetrofitClient.instance.sendOtp(request).enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@ForgotPasswordActivity, "OTP sent to email", Toast.LENGTH_SHORT).show()

                            startActivity(
                                Intent(this@ForgotPasswordActivity, ForgotPassword2Activity::class.java).apply {
                                    putExtra("email", email)
                                }
                            )
                        } else {
                            Toast.makeText(this@ForgotPasswordActivity, "Failed: ${response.body()?.message}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("ForgotPassword", "Error: ${t.message}")
                        Toast.makeText(this@ForgotPasswordActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }


            startActivity(
                Intent(this, ForgotPassword2Activity::class.java)
            )
        }
    }
}
