package com.grupo3.historyar.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourBigBinding
import com.grupo3.historyar.models.Tour
import com.squareup.picasso.Picasso

class CloseExperiencesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FragmentTourBigBinding.bind(view)

    fun bind(tour: Tour, onItemSelected: (String) -> Unit) {
        binding.tvTourName.text = tour.name
        binding.tvTourDistance.text = "450m"
        binding.tvTourDuration.text = tour.duration.toString() + " minutos"
        binding.tvTourDescription.text = tour.description
        Picasso.get().load(tour.image).into(binding.ivTourImage)
        if (tour.isFavorite) {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_24)
        } else {
            binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_border_24)
        }

        binding.ivTourImage.setOnClickListener{onItemSelected(tour.id)}
        binding.ivPlayIcon.setOnClickListener{onItemSelected(tour.id)}
    }
}