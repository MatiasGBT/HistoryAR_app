package com.grupo3.historyar.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.databinding.FragmentCommentBinding
import com.grupo3.historyar.models.Qualification
import com.squareup.picasso.Picasso

class QualificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FragmentCommentBinding.bind(view)

    fun bind(qualification: Qualification) {
        binding.tvCommentName.text = qualification.user.fullName
        binding.tvCommentDescription.text = qualification.comment
        binding.tvCommentScore.text = qualification.score.toString()
        Picasso.get().load(qualification.user.photo).into(binding.ivCommentImage)
    }
}