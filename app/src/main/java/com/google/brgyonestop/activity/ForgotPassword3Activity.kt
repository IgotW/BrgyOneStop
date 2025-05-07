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
import com.google.brgyonestop.request.ResetPasswordRequest
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword3Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password3)

        val image_f3backarrow = findViewById<ImageView>(R.id.imageview_f3backarrow)
        val edittext_forgot3_password = findViewById<EditText>(R.id.edittext_forgot3_password)
        val edittext_forgot3_confirmpassword = findViewById<EditText>(R.id.edittext_forgot3_confirmpassword)
        val button_forgotsubmit = findViewById<Button>(R.id.button_forgotsubmit)
        var userId = ""

        intent?.let {
            it.getStringExtra("userId")?.let { id ->
                userId = id
            }
        }

        button_forgotsubmit.setOnClickListener {
            val password = edittext_forgot3_password.text.toString().trim()
            val confirmPassword = edittext_forgot3_confirmpassword.text.toString().trim()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = ResetPasswordRequest(userId, password)

            RetrofitClient.instance.resetPassword(request).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@ForgotPassword3Activity, "Password reset successfully", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this@ForgotPassword3Activity, LoginActivity::class.java)
                        )
                        finish()
                    } else {
                        Toast.makeText(this@ForgotPassword3Activity, "Failed to reset password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@ForgotPassword3Activity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        image_f3backarrow.setOnClickListener {
            startActivity(
                Intent(this, ForgotPassword2Activity::class.java)
            )
        }

    }
}