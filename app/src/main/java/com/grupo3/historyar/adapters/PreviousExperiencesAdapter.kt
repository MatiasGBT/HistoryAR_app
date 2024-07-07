package com.grupo3.historyar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.view_holders.TourMiniViewHolder

class PreviousExperiencesAdapter(
    private var tourList: List<Tour> = emptyList(),
    private val onItemSelected: (String) -> Unit,
    private val onFavSelected: (String) -> Unit
) : RecyclerView.Adapter<TourMiniViewHolder>() {

    fun updateList(tourList: List<Tour>) {
        this.tourList = tourList
        notifyDataSetChanged() //No es necesario notificar por posición ya que la lista va a ser pequeña
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourMiniViewHolder {
        return TourMiniViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_tour_mini, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tourList.size
    }

    override fun onBindViewHolder(holder: TourMiniViewHolder, position: Int) {
        holder.bind(tourList[position], onItemSelected, onItemFavSelected = {
            tourList.forEach { tour -> tour.isFavorite = tour.id == it }
            updateList(tourList)
            onFavSelected(it)
        })
    }
}