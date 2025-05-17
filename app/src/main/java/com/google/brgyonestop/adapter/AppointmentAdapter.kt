package com.google.brgyonestop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.brgyonestop.R
import com.google.brgyonestop.models.Announcement
import com.google.brgyonestop.models.Appointment
import com.google.brgyonestop.models.Appointments

class AppointmentAdapter (
    private val context: Context,
    private val appointment: List<Appointment>
):BaseAdapter(){
    override fun getCount(): Int = appointment.size

    override fun getItem(position: Int): Any = appointment[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false)

        val appointment = appointment[position]

        val allappointment_title = view.findViewById<TextView>(R.id.allappointment_title)
        val allappointment_purpose = view.findViewById<TextView>(R.id.allappointment_purpose)
        val allappointment_date = view.findViewById<TextView>(R.id.allappointment_date)
        val allappointment_time = view.findViewById<TextView>(R.id.allappointment_time)
        val allappointment_created = view.findViewById<TextView>(R.id.allappointment_created)
        val allappointment_status = view.findViewById<TextView>(R.id.allappointment_status)
        val image_view = view.findViewById<ImageView>(R.id.imageview_status_color)


        allappointment_title.setText(appointment.title)
        allappointment_purpose.setText(appointment.purpose)
        allappointment_date.setText(appointment.date)
        allappointment_time.setText(appointment.time)
        allappointment_created.setText(appointment.createdAt)
        allappointment_status.setText(appointment.status)

        when (appointment.status) {
            "Pending" -> image_view.setColorFilter(context.getColor(R.color.status_pending))
            "Confirmed" -> image_view.setColorFilter(context.getColor(R.color.status_resolved))
            "Cancelled" -> image_view.setColorFilter(context.getColor(R.color.status_rejected))
            else -> image_view.setColorFilter(context.getColor(R.color.black)) // Default
        }

        return view
    }

}