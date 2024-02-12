package com.grupo3.historyar.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourMiniBinding
import com.grupo3.historyar.models.Tour
import com.squareup.picasso.Picasso

class TourMiniViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FragmentTourMiniBinding.bind(view)

    fun bind(tour: Tour, onItemSelected: (String) -> Unit) {
        binding.tvTourName.text = tour.name
        binding.tvTourDuration.text = tour.duration.toString() + " minutos"
        Picasso.get().load(tour.image).into(binding.ivTourImage)
        if (tour.isFavorite) {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_24)
        } else {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_border_24)
        }

        binding.cvTour.setOnClickListener{onItemSelected(tour.id)}
    }
}