package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.brgyonestop.R
import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.request.AnnouncementRequest
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminCreateAnnouncementActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create_announcement)

        val imageview_create_back = findViewById<ImageView>(R.id.imageview_create_back)
        val edittext_create_title = findViewById<EditText>(R.id.edittext_create_title)
        val textinputedittext_create_description = findViewById<TextInputEditText>(R.id.textinputedittext_create_description)
        val button_create_post = findViewById<Button>(R.id.button_create_post)


        button_create_post.setOnClickListener {
            val title = edittext_create_title.text.toString()
            val description = textinputedittext_create_description.text.toString()

            val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val token = sharedPref.getString("token", null)

            if (token != null) {
                val bearerToken = "Bearer $token"
                val request = AnnouncementRequest(
                    title,
                    description
                )

                RetrofitClient.instance.createAnnouncement(bearerToken, request)
                    .enqueue(object : Callback<AnnouncementResponse> {
                        override fun onResponse(
                            call: Call<AnnouncementResponse>,
                            response: Response<AnnouncementResponse>
                        ) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                Toast.makeText(this@AdminCreateAnnouncementActivity, "Announcement posted!", Toast.LENGTH_SHORT).show()
                                startActivity(
                                    Intent(this@AdminCreateAnnouncementActivity, AdminDashboardActivity::class.java)
                                )
                                finish()
                            } else {
                                val errorMessage = response.body()?.message ?: "Failed to post"
                                Toast.makeText(this@AdminCreateAnnouncementActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                            Toast.makeText(this@AdminCreateAnnouncementActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            }
        }

        imageview_create_back.setOnClickListener {
            startActivity(
                Intent(this, AdminDashboardActivity::class.java)
            )
        }
    }
}