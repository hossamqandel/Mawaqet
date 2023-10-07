package com.devabits.mawaqet.feature_mawaqet.presentation

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devabits.mawaqet.R
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MawaqetVH (itemView: View): RecyclerView.ViewHolder(itemView) {
    private val prayerName: TextView = itemView.findViewById(R.id.tv_PrayerName)
    private val prayerTime: TextView = itemView.findViewById(R.id.tv_PrayerTime)
    private val card: MaterialCardView = itemView.findViewById(R.id.cardMawaqet)

    private val currentTime = Date()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val formattedTime = timeFormat.format(currentTime)

    fun bind(prayers: List<Prayer>){
        val currentItem = prayers[adapterPosition]
        prayerName.text = currentItem.prayerName
        prayerTime.text = currentItem.prayerTime
        setItemCardColor()
    }

    private fun setItemCardColor(){
        if (adapterPosition %2 == 0){
            card.setBackgroundColor(Color.WHITE)
        } else card.setBackgroundColor(Color.LTGRAY)
    }

    fun markNextSalah(prayers: List<Prayer>){
        val currentItem = prayers[adapterPosition]
        val isTodaySalawatFinished = formattedTime < "23:00" && formattedTime > prayers.last().prayerTime
        val salahToMark = prayers.firstOrNull { it.prayerTime > formattedTime }
        if (currentItem.prayerTime.equals(salahToMark?.prayerTime, true)){
            prayerName.setBackgroundColor(Color.CYAN)
            prayerTime.setBackgroundColor(Color.CYAN)
            return
        }
        if (isTodaySalawatFinished){
            //This will not mark any salah as a next prayer
            prayerName.setBackgroundColor(Color.TRANSPARENT)
            prayerTime.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}