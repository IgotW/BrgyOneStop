package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.brgyonestop.R
import com.google.brgyonestop.adapter.UserAdapter
import com.google.brgyonestop.models.Users
import com.google.brgyonestop.response.UsersResponse
import com.google.brgyonestop.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminResidentsListActivity : Activity() {
    private lateinit var listview_residentlist: ListView
    private lateinit var userAdapter: UserAdapter
    private val usersList = mutableListOf<Users>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_residents_list)

        val imageview_residentlist_back = findViewById<ImageView>(R.id.imageview_residentlist_back)
        listview_residentlist = findViewById(R.id.listview_residentlist)

        imageview_residentlist_back.setOnClickListener {
            startActivity(Intent(this, AdminDashboardActivity::class.java))
        }

    }
    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if (token != null) {
            val bearerToken = "Bearer $token"
            fetchAllUsers(bearerToken)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun fetchAllUsers(token: String) {
        RetrofitClient.instance.getAllUsers(token).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val users = response.body()!!.users

                    userAdapter = UserAdapter(this@AdminResidentsListActivity, users)
                    listview_residentlist.adapter = userAdapter

                    listview_residentlist.setOnItemClickListener { _, _, position, _ ->
                        val selectedUser = users[position]

                        startActivity(
                            Intent(this@AdminResidentsListActivity, AdminResidentProfileActivity::class.java).apply {
                                putExtra("firstname", selectedUser.firstName)
                                putExtra("middlename", selectedUser.middleName)
                                putExtra("lastname", selectedUser.lastName)
                                putExtra("suffix", selectedUser.suffix)
                                putExtra("birthdate", selectedUser.birthdate)
                                putExtra("gender", selectedUser.gender)
                                putExtra("phone", selectedUser.phoneNumber)
                                putExtra("email", selectedUser.email)
                                putExtra("purok", selectedUser.purok)
                                putExtra("street", selectedUser.street)
                                putExtra("barangay", selectedUser.barangay)
                                putExtra("municipal", selectedUser.municipality)
                                putExtra("province", selectedUser.province)
                                putExtra("zip", selectedUser.zipCode)

                            }
                        )
                        Toast.makeText(this@AdminResidentsListActivity, "User: ${selectedUser.username}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AdminResidentsListActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Toast.makeText(this@AdminResidentsListActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}