package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.brgyonestop.R

class Register2Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val edittext_reg_firstname = findViewById<EditText>(R.id.edittext_reg_firstname)
        val edittext_reg_middlename = findViewById<EditText>(R.id.edittext_reg_middlename)
        val edittext_reg_lastname = findViewById<EditText>(R.id.edittext_reg_lastname)
        val edittext_reg_suffix = findViewById<EditText>(R.id.edittext_reg_suffix)
        val edittext_reg_birthdate = findViewById<EditText>(R.id.edittext_reg_birthdate)
        val edittext_reg_gender = findViewById<EditText>(R.id.edittext_reg_gender)
        val button_register2_next = findViewById<Button>(R.id.button_register2_next)
        val button_register2_cancel = findViewById<Button>(R.id.button_register2_cancel)
        var username = ""
        var email = ""
        var phone = ""
        var password = ""

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
        }

        button_register2_next.setOnClickListener {
            val firstname = edittext_reg_firstname.text.toString()
            val middlename = edittext_reg_middlename.text.toString()
            val lastname = edittext_reg_lastname.text.toString()
            val suffix = edittext_reg_suffix.text.toString()

            var hasError = false

            if(firstname.isEmpty()){
                edittext_reg_firstname.error = "Firstname is required"
                hasError = true
            }
            if (lastname.isEmpty()){
                edittext_reg_lastname.error = "Lastname is required"
                hasError = true
            }

            if (hasError) return@setOnClickListener

            startActivity(
                Intent(this, Register3Activity::class.java).apply {
                    putExtra("username", username)
                    putExtra("email", email)
                    putExtra("phone", phone)
                    putExtra("password", password)
                    putExtra("firstname", firstname)
                    putExtra("middlename", middlename)
                    putExtra("lastname", lastname)
                    putExtra("suffix", suffix)
                }
            )
        }

        button_register2_cancel.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }
}