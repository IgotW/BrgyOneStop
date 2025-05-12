package com.google.brgyonestop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.brgyonestop.R
import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.models.Complaint

class AnnouncementAdapter (
    private val context: Context,
    private val announcements: List<Announcement>
):BaseAdapter(){
    override fun getCount(): Int = announcements.size

    override fun getItem(position: Int): Any = announcements[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_announcement, parent, false)

        val announcement = announcements[position]

        val title = view.findViewById<TextView>(R.id.tvTitle)
        val description = view.findViewById<TextView>(R.id.tvDescription)
        val date = view.findViewById<TextView>(R.id.tvDate)

        title.text = announcement.title
        description.text = announcement.description
        date.text = announcement.createdAt

        return view
    }

}
