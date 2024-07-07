package com.grupo3.historyar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.models.Qualification
import com.grupo3.historyar.view_holders.QualificationViewHolder

class CommentListAdapter(
    private var qualificationList: List<Qualification> = emptyList()
) : RecyclerView.Adapter<QualificationViewHolder>() {

    fun updateList(qualificationList: List<Qualification>) {
        //TODO: Modificar notifyDataSetChanged() para optimizar el código ya que esta lista puede ser larga
        this.qualificationList = qualificationList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualificationViewHolder {
        return QualificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return qualificationList.size
    }

    override fun onBindViewHolder(holder: QualificationViewHolder, position: Int) {
        holder.bind(qualificationList[position])
    }
}