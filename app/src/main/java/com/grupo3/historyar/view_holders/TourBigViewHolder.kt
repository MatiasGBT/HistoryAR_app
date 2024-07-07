package com.grupo3.historyar.view_holders

import android.location.Location
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourBigBinding
import com.grupo3.historyar.models.Tour
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class TourBigViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FragmentTourBigBinding.bind(view)

    fun bind(tour: Tour, onItemInfoSelected: (String) -> Unit, onItemPlaySelected: (String) -> Unit, onItemFavSelected: (String) -> Unit, currentUserLocation: Location) {
        val destiny = Location("Destiny")
        destiny.latitude = tour.latitude.toDouble()
        destiny.longitude = tour.longitude.toDouble()

        binding.tvTourName.text = tour.name
        binding.tvTourDistance.text = currentUserLocation.distanceTo(destiny).roundToInt().toString() + "m"
        binding.tvTourDuration.text = tour.duration
        binding.tvTourDescription.text = tour.description
        Picasso.get().load(tour.image).into(binding.ivTourImage)
        if (tour.isFavorite) {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_24)
        } else {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_border_24)
        }

        binding.clTourInfo.setOnClickListener{onItemInfoSelected(tour.id)}
        binding.ivPlayIcon.setOnClickListener{onItemPlaySelected(tour.id)}
        binding.ivStarIcon.setOnClickListener{
            if (!tour.isFavorite) {
                tour.isFavorite =  true
                onItemFavSelected(tour.id)
            }
        }
    }
}