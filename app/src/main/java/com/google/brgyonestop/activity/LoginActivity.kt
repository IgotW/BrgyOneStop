package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.brgyonestop.R
import com.google.brgyonestop.response.LoginResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edittext_login_username = findViewById<EditText>(R.id.edittext_login_username)
        val edittext_login_password = findViewById<EditText>(R.id.edittext_login_password)
        val textview_forgotpassword = findViewById<TextView>(R.id.textview_forgotpassword)
        val textview_register = findViewById<TextView>(R.id.textview_register)
        val button_login = findViewById<Button>(R.id.button_login)

        textview_register.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        textview_forgotpassword.setOnClickListener {
            startActivity(
                Intent(this, ForgotPasswordActivity::class.java)
            )
        }
        button_login.setOnClickListener {
            val username = edittext_login_username.text.toString()
            val password = edittext_login_password.text.toString()

            val body = mapOf(
                "username" to username,
                "password" to password
            )

            RetrofitClient.instance.loginUser(body).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val loginResponse = response.body()!!
                        val role = loginResponse.role

                        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putString("token", loginResponse.token)
                        editor.apply()

                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                        Log.d("API_LOGIN", response.body().toString())

                        if(role == "admin"){
                            startActivity(
                                Intent(this@LoginActivity, AdminDashboardActivity::class.java)
                            )
                        } else{
                            startActivity(
                                Intent(this@LoginActivity, DashboardActivity::class.java)
                            )
                        }
                        finish()
                        // You can navigate to the next activity
                        // startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        Log.e("API_LOGIN_ERROR", response.errorBody()?.string().toString())
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_LOGIN_EXCEPTION", t.toString())
                }
            })
        }
    }
}