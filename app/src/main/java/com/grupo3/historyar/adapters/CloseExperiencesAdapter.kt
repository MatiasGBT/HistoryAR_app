package com.grupo3.historyar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.view_holders.CloseExperiencesViewHolder

class CloseExperiencesAdapter(
    var tourList: List<Tour> = emptyList(),
    private val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<CloseExperiencesViewHolder>() {

    fun updateList(tourList: List<Tour>) {
        this.tourList = tourList
        notifyDataSetChanged() //No es necesario notificar por posición ya que la lista va a ser pequeña
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloseExperiencesViewHolder {
        return CloseExperiencesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_tour_big, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tourList.size
    }

    override fun onBindViewHolder(holder: CloseExperiencesViewHolder, position: Int) {
        holder.bind(tourList[position], onItemSelected)
    }
}