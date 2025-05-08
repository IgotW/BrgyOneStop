package com.google.brgyonestop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.brgyonestop.R
import com.google.brgyonestop.models.Complaint

class ComplaintAdapter (
    private val context: Context,
    private val complaints: List<Complaint>
): BaseAdapter(){
    override fun getCount(): Int = complaints.size

    override fun getItem(position: Int): Any = complaints[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_complaint, parent, false)

        val complaint = complaints[position]


        val categoryText = view.findViewById<TextView>(R.id.categoryText)
        val detailsText = view.findViewById<TextView>(R.id.detailsText)
        val statusText = view.findViewById<TextView>(R.id.statusText)
        val createdAtText = view.findViewById<TextView>(R.id.createdAtText)
        val userNameText = view.findViewById<TextView>(R.id.userNameText)
        val userPurokText = view.findViewById<TextView>(R.id.userPurokText)
        val userStreetText = view.findViewById<TextView>(R.id.userStreetText)


        categoryText.text = complaint.category
        detailsText.text = complaint.details
        statusText.text = complaint.status
        createdAtText.text = complaint.createdAt

        if (complaint.anonymous) {
            userNameText.text = "Anonymous"
            userPurokText.text = ""
            userStreetText.text = ""
        } else {
            userNameText.text = complaint.user.username
            userPurokText.text = complaint.user.purok ?: "N/A"
            userStreetText.text = complaint.user.street ?: "N/A"
        }

        return view
    }

}