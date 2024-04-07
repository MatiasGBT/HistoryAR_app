package com.grupo3.historyar.ui.tour_big

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourBigBinding
import com.grupo3.historyar.ui.view_models.TourViewModel
import com.squareup.picasso.Picasso

const val ID_BUNDLE = "id_bundle"
class TourBigFragment : Fragment() {
    private var id: String? = null
    private val tourViewModel: TourViewModel by activityViewModels()
    private var _binding: FragmentTourBigBinding? = null
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
        _binding = FragmentTourBigBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initViewModel()
    }

    private fun initViewModel() {
        tourViewModel.tourModel.observe(viewLifecycleOwner, Observer {
            binding.tvTourName.text = it.name
            binding.tvTourDistance.text = "450m"
            binding.tvTourDuration.text = it.duration.toString() + " minutos"
            binding.tvTourDescription.text = it.description
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