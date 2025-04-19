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
            startActivity(
                Intent(this, Register3Activity::class.java).apply {
                    putExtra("username", username)
                    putExtra("email", email)
                    putExtra("phone", phone)
                    putExtra("password", password)
                    putExtra("firstname", edittext_reg_firstname.text.toString())
                    putExtra("middlename", edittext_reg_middlename.text.toString())
                    putExtra("lastname", edittext_reg_lastname.text.toString())
                    putExtra("suffix", edittext_reg_suffix.text.toString())
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