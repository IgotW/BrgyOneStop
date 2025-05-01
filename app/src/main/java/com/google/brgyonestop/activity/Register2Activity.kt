package com.google.brgyonestop.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.brgyonestop.R
import java.util.Calendar

class Register2Activity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val edittext_reg_firstname = findViewById<EditText>(R.id.edittext_reg_firstname)
        val edittext_reg_middlename = findViewById<EditText>(R.id.edittext_reg_middlename)
        val edittext_reg_lastname = findViewById<EditText>(R.id.edittext_reg_lastname)
        val edittext_reg_suffix = findViewById<EditText>(R.id.edittext_reg_suffix)
        val edittext_reg_birthdate = findViewById<EditText>(R.id.edittext_reg_birthdate)
        val edittext_reg_gender = findViewById<Spinner>(R.id.spinner_reg_gender)
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

        edittext_reg_birthdate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedMonth = String.format("%02d", selectedMonth + 1)
                    val formattedDay = String.format("%02d", selectedDay)
                    val formattedDate = "$formattedMonth/$formattedDay/$selectedYear"
                    edittext_reg_birthdate.setText(formattedDate)
                }, year, month, day)
            datePickerDialog.show()
        }

        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edittext_reg_gender.adapter = adapter


        button_register2_next.setOnClickListener {
            val firstname = edittext_reg_firstname.text.toString()
            val middlename = edittext_reg_middlename.text.toString()
            val lastname = edittext_reg_lastname.text.toString()
            val suffix = edittext_reg_suffix.text.toString()
            val bdate = edittext_reg_birthdate.text.toString()
            val gender = edittext_reg_gender.selectedItem.toString()

            var hasError = false

            if(firstname.isEmpty()){
                edittext_reg_firstname.error = "Firstname is required"
                hasError = true
            }
            if (lastname.isEmpty()){
                edittext_reg_lastname.error = "Lastname is required"
                hasError = true
            }
            if(bdate.isEmpty()){
                edittext_reg_birthdate.error = "Birth Date is required"
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
                    putExtra("birthdate", bdate)
                    putExtra("gender", gender)
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