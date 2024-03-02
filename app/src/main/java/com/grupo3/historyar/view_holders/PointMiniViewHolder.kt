package com.grupo3.historyar.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.databinding.FragmentMiniPointOfInterestBinding
import com.grupo3.historyar.models.PointOfInterest

class PointMiniViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FragmentMiniPointOfInterestBinding.bind(view)

    fun bind(point: PointOfInterest) {
        binding.tvPointName.text = point.name
    }
}