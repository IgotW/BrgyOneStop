package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.request.ChangePasswordRequest
import com.google.brgyonestop.response.ApiResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : Activity() {
    private lateinit var changepass_currentpassword: EditText
    private lateinit var changepass_newpassword: EditText
    private lateinit var changepass_confirmpassword: EditText
    private lateinit var button_changepass_reset: Button
    private lateinit var button_changepass_cancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        changepass_currentpassword = findViewById(R.id.edittext_changepass_currentpassword)
        changepass_newpassword = findViewById(R.id.edittext_changepass_newpassword)
        changepass_confirmpassword = findViewById(R.id.edittext_changepass_confirmpassword)
        button_changepass_reset = findViewById(R.id.button_changepass_reset)
        button_changepass_cancel = findViewById(R.id.button_changepass_cancel)

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        button_changepass_reset.setOnClickListener {
            val oldPass = changepass_currentpassword.text.toString()
            val newPass = changepass_newpassword.text.toString()
            val confirmPass = changepass_confirmpassword.text.toString()

            if (newPass != confirmPass){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(token != null){
                val request = ChangePasswordRequest(oldPass, newPass)
                changePassword("Bearer $token", request)
            }
        }
    }
    private fun changePassword(token: String, request: ChangePasswordRequest) {
        RetrofitClient.instance.changePassword(token, request)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@ChangePasswordActivity, "Password changed!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ChangePasswordActivity, response.body()?.message ?: "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@ChangePasswordActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}