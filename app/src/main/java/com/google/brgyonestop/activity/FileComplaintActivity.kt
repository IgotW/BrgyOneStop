package com.google.brgyonestop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.brgyonestop.R
import com.google.brgyonestop.utils.ApiService
import com.google.brgyonestop.utils.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FileComplaintActivity : Activity() {
    private var selectedFileUri: Uri? = null
    private val FILE_SELECT_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_complaint)

        val spinner_filecomplaint_catergory = findViewById<Spinner>(R.id.spinner_filecomplaint_catergory)
        val multilineTextBox = findViewById<EditText>(R.id.multilineTextBox)
        val button_filecomplaint_choosefiles = findViewById<Button>(R.id.button_filecomplaint_choosefiles)
        val checkbox_filecomplaint_anonymously = findViewById<CheckBox>(R.id.checkbox_filecomplaint_anonymously)
        val button_filecomplaint_submit = findViewById<Button>(R.id.button_filecomplaint_submit)
        val button_filecomplaint_cancel = findViewById<Button>(R.id.button_filecomplaint_cancel)
        val imageview_filecomplaint_back = findViewById<ImageView>(R.id.imageview_filecomplaint_back)

        button_filecomplaint_choosefiles.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, FILE_SELECT_CODE)
        }
        button_filecomplaint_submit.setOnClickListener {
            val category = spinner_filecomplaint_catergory.selectedItem.toString()
            val details = multilineTextBox.text.toString()
            val isAnonymous = checkbox_filecomplaint_anonymously.isChecked

            if (category.isNotEmpty() && details.isNotEmpty()) {
                uploadComplaint(category, details, isAnonymous)
                startActivity(
                    Intent(this, DashboardActivity::class.java)
                )
            } else {
                Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_SHORT).show()
            }
        }
        button_filecomplaint_cancel.setOnClickListener {
            startActivity(
                Intent(this, DashboardActivity::class.java)
            )
        }
        imageview_filecomplaint_back.setOnClickListener {
            startActivity(
                Intent(this, UserAllFileComplaintsActivity::class.java)
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            selectedFileUri?.let { uri ->
                val fileName = getFileNameFromUri(uri)
                val textview_filecomplaint_selected_file = findViewById<TextView>(R.id.textview_filecomplaint_selected_file)
                textview_filecomplaint_selected_file.text = fileName ?: "File selected"
            }
        }
    }
    private fun uploadComplaint(category: String, details: String, anonymous: Boolean) {
        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token == null) {
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.instance

        val categoryPart = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val detailsPart = details.toRequestBody("text/plain".toMediaTypeOrNull())
        val anonymousPart = anonymous.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val filePart: MultipartBody.Part? = selectedFileUri?.let { uri ->
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val fileName = getFileNameFromUri(uri) ?: "upload_file"
                val tempFile = File.createTempFile("upload", fileName, cacheDir)

                tempFile.outputStream().use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }

                val mediaType = contentResolver.getType(uri)?.toMediaTypeOrNull()
                val requestFile = tempFile.asRequestBody(mediaType)
                MultipartBody.Part.createFormData("file", fileName, requestFile)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to prepare file: ${e.message}", Toast.LENGTH_LONG).show()
                null
            }
        }

        val call = apiService.fileComplaint("Bearer $token", categoryPart, detailsPart, anonymousPart, filePart)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FileComplaintActivity, "Complaint filed successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@FileComplaintActivity, "Failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@FileComplaintActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun getFileNameFromUri(uri: Uri): String? {
        var result: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) {
                    result = it.getString(nameIndex)
                }
            }
        }
        return result
    }
}