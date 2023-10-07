package com.devabits.mawaqet.feature_mawaqet.presentation

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devabits.mawaqet.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MawaqetVH (itemView: View): RecyclerView.ViewHolder(itemView) {
    private val prayerName: TextView = itemView.findViewById(R.id.tv_PrayerName)
    private val prayerTime: TextView = itemView.findViewById(R.id.tv_PrayerTime)
    private val currentTime = Date()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val formattedTime = timeFormat.format(currentTime)

    fun bind(prayers: List<Prayer>){
        val currentItem = prayers[adapterPosition]
        prayerName.text = currentItem.prayerName
        prayerTime.text = currentItem.prayerTime
    }

    fun markNextSalah(prayers: List<Prayer>){
        val currentItem = prayers[adapterPosition]
        val salahToMark = prayers.first { it.prayerTime > formattedTime }
        if (currentItem.prayerTime.equals(salahToMark.prayerTime, true)){
            prayerName.setBackgroundColor(Color.CYAN)
            prayerTime.setBackgroundColor(Color.CYAN)
            return
        }
    }
}