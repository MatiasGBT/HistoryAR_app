package com.grupo3.historyar.ui.tour_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourProfileBinding
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.TourViewModel
import com.squareup.picasso.Picasso

const val ID_BUNDLE = "id_bundle"
class TourProfileFragment : Fragment() {
    private var id: String? = null
    private val tourViewModel: TourViewModel by activityViewModels()
    private var _binding: FragmentTourProfileBinding? = null
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
        _binding = FragmentTourProfileBinding.inflate(inflater, container, false)
        initViewMode()
        return binding.root
    }

    private fun initViewMode() {
        tourViewModel.tourModel.observe(viewLifecycleOwner, Observer {
            binding.tvTourName.text = it.name
            Picasso.get().load(it.image).into(binding.ivTourImage)
        })
        tourViewModel.getTour(id!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}