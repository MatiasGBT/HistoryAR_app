package com.grupo3.historyar.ui.tour_mini

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourMiniBinding
import com.grupo3.historyar.ui.view_models.TourViewModel
import com.squareup.picasso.Picasso

const val ID_BUNDLE = "id_bundle"
class TourMiniFragment : Fragment() {
    private var id: String? = null
    private val tourViewModel: TourViewModel by activityViewModels()
    private var _binding: FragmentTourMiniBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTourMiniBinding.inflate(inflater, container, false)
        initViewMode()
        return binding.root
    }

    private fun initViewMode() {
        tourViewModel.tourModel.observe(viewLifecycleOwner, Observer {
            binding.tvTourName.text = it.name
            binding.tvTourDuration.text = it.duration.toString() + " minutos"
            Picasso.get().load(it.image).into(binding.ivTourImage)
            if (it.isFavorite) {
                binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_24)
            } else {
                binding.ivStarIcon.setImageResource(R.drawable.ic_round_star_border_24)
            }
        })
        tourViewModel.getTour(ID_BUNDLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}