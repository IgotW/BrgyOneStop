package com.google.brgyonestop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.brgyonestop.R
import com.google.brgyonestop.models.Users

class UserAdapter (
    private val context: Context,
    private val users: List<Users>
):BaseAdapter(){
    override fun getCount(): Int = users.size

    override fun getItem(position: Int): Any = users[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_residents, parent, false)

        val user = users[position]
        val textViewUsername = view.findViewById<TextView>(R.id.resident_name)
        val textViewEmail = view.findViewById<TextView>(R.id.resident_email)
        val imageview_profile_pic = view.findViewById<ImageView>(R.id.imageview_profile_pic)

        val fullName = "${user.firstName} ${user.middleName} ${user.lastName} ${user.suffix}".trim()
        textViewUsername.text = fullName
        textViewEmail.text = user.email
        imageview_profile_pic.setImageResource(R.drawable.img_nocontent)


        return view
    }

}