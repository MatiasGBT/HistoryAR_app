package com.grupo3.historyar.ui.tour_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.grupo3.historyar.R
import com.grupo3.historyar.adapters.TourListAdapter
import com.grupo3.historyar.databinding.FragmentTourListBinding
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.TourViewModel

class TourListFragment : Fragment() {
    private val tourViewModel: TourViewModel by activityViewModels()
    private var _binding: FragmentTourListBinding? = null
    private val binding get() = _binding!!
    private lateinit var tourListAdapter: TourListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTourListBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initAllToursAdapter()
        observeAllToursMutableData()
        tourViewModel.getAll()
    }

    private fun initAllToursAdapter() {
        tourListAdapter = TourListAdapter { navigateToTourDetail(it) }
        binding.rvAllExperiences.setHasFixedSize(true)
        binding.rvAllExperiences.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAllExperiences.adapter = tourListAdapter
    }

    private fun observeAllToursMutableData() {
        tourViewModel.allToursAreLoading.observe(viewLifecycleOwner, Observer {
            binding.pbAllExperiences.isVisible = it
            binding.rvAllExperiences.isVisible = !it
        })
        tourViewModel.allToursModel.observe(viewLifecycleOwner, Observer {
            tourListAdapter.updateList(it)
        })
    }

    private fun navigateToTourDetail(id: String) {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_tourListFragment_to_tourDetail, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}