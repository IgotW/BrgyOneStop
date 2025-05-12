package com.google.brgyonestop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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
        val address = view.findViewById<TextView>(R.id.address)
        val status_color = view.findViewById<ImageView>(R.id.imageview_status_color)


        categoryText.text = complaint.category
        detailsText.text = complaint.details
        statusText.text = complaint.status
        createdAtText.text = complaint.createdAt

        if (complaint.anonymous) {
            userNameText.text = "Anonymous"
            address.text = ""
        } else {
            userNameText.text = complaint.user.username
            val purok = complaint.user.purok ?: ""
            val street = complaint.user.street ?: ""
            val barangay = complaint.user.barangay ?: ""
            val municipality = complaint.user.municipality ?: ""
            val province = complaint.user.province ?: ""
            address.text = listOf(purok, street, barangay, municipality, province).filter { it.isNotBlank() }.joinToString(", ")
        }

        // Set color based on status
        when (complaint.status) {
            "Pending" -> status_color.setColorFilter(context.getColor(R.color.status_pending))
            "In Progress" -> status_color.setColorFilter(context.getColor(R.color.status_in_progress))
            "Resolved" -> status_color.setColorFilter(context.getColor(R.color.status_resolved))
            "Rejected" -> status_color.setColorFilter(context.getColor(R.color.status_rejected))
            else -> status_color.setColorFilter(context.getColor(R.color.black)) // Default
        }


        return view
    }

}