package com.devabits.mawaqet.feature_mawaqet.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devabits.mawaqet.R
import com.devabits.mawaqet.core.enums.SalawatEnum
import com.devabits.mawaqet.feature_mawaqet.domain.models.Timings

class MawaqetAdapter : RecyclerView.Adapter<MawaqetVH>() {

    private val data = mutableListOf<Prayer>()

    fun setPrayers(timings: Timings) {
        data.add(Prayer(prayerName = SalawatEnum.FAJR.salat, prayerTime = timings.fajr))
        data.add(Prayer(prayerName = SalawatEnum.DHUHR.salat, prayerTime = timings.dhuhr))
        data.add(Prayer(prayerName = SalawatEnum.ASR.salat, prayerTime = timings.asr))
        data.add(Prayer(prayerName = SalawatEnum.MAGHRIB.salat, prayerTime = timings.maghrib))
        data.add(Prayer(prayerName = SalawatEnum.ISHA.salat, prayerTime = timings.isha))
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MawaqetVH {
        val viewHolder = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_mawaqet_salawat,
            parent,
            false
        )
        return MawaqetVH(viewHolder)

    }

    override fun onBindViewHolder(holder: MawaqetVH, position: Int) {
        holder.bind(data)
        holder.markNextSalah(data)
    }

    override fun getItemCount(): Int = data.size ?: 0
}