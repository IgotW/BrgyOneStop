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
import com.google.brgyonestop.R
import com.google.brgyonestop.response.AnnouncementResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAnnouncementDetailsActivity : Activity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var announcementId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_announcement_details)

        editTextTitle = findViewById(R.id.edittext_edit_title)
        editTextDescription = findViewById(R.id.textinputedittext_edit_description)
        buttonUpdate = findViewById(R.id.button_edit_post)

        val imageview_edit_back = findViewById<ImageView>(R.id.imageview_edit_back)

        intent?.let {
            it.getStringExtra("announcement_id")?.let {announcementid ->
                announcementId = announcementid
            }
            it.getStringExtra("announcement_title")?.let { announcementtitle ->
                editTextTitle.setText(announcementtitle)
            }
            it.getStringExtra("announcement_description")?.let { announcementdescription ->
                editTextDescription.setText(announcementdescription)
            }
        }

        buttonUpdate.setOnClickListener {
            updateAnnouncement()
        }

        imageview_edit_back.setOnClickListener {
            startActivity(
                Intent(this, AdminAllAnnouncementActivity::class.java)
            )
        }

    }
    private fun updateAnnouncement() {
        val title = editTextTitle.text.toString().trim()
        val description = editTextDescription.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null) ?: return
        val bearerToken = "Bearer $token"

        val updateData = mapOf("title" to title, "description" to description)

        val call = RetrofitClient.instance.updateAnnouncement(bearerToken, announcementId, updateData)
        call.enqueue(object : Callback<AnnouncementResponse> {
            override fun onResponse(call: Call<AnnouncementResponse>, response: Response<AnnouncementResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AdminAnnouncementDetailsActivity, "Updated successfully!", Toast.LENGTH_SHORT).show()
                    finish() // go back
                } else {
                    Toast.makeText(this@AdminAnnouncementDetailsActivity, "Failed to update", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AnnouncementResponse>, t: Throwable) {
                Toast.makeText(this@AdminAnnouncementDetailsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}