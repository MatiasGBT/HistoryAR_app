package com.grupo3.historyar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.view_holders.TourBigViewHolder

class TourListAdapter(
    private var tourList: List<Tour> = emptyList(),
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<TourBigViewHolder>() {

    fun updateList(tourList: List<Tour>) {
        //TODO: Modificar notifyDataSetChanged() para optimizar el código ya que esta lista puede ser larga
        this.tourList = tourList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourBigViewHolder {
        return TourBigViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_tour_big, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tourList.size
    }

    override fun onBindViewHolder(holder: TourBigViewHolder, position: Int) {
        holder.bind(tourList[position], onItemSelected)
    }
}