package com.grupo3.historyar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.view_holders.PointMiniViewHolder

class MiniPointAdapter(
    private var pointList: List<PointOfInterest> = emptyList()
) : RecyclerView.Adapter<PointMiniViewHolder>() {

    fun updateList(pointList: List<PointOfInterest>) {
        this.pointList = pointList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointMiniViewHolder {
        return PointMiniViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_mini_point_of_interest, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pointList.size
    }

    override fun onBindViewHolder(holder: PointMiniViewHolder, position: Int) {
        holder.bind(pointList[position])
    }
}