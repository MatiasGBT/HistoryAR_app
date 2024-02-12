package com.grupo3.historyar.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.R
import com.grupo3.historyar.adapters.CloseExperiencesAdapter
import com.grupo3.historyar.adapters.PreviousExperiencesAdapter
import com.grupo3.historyar.databinding.FragmentHomeBinding
import com.grupo3.historyar.ui.subscription.SubscriptionFragment
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.PreferencesViewModel
import com.grupo3.historyar.ui.view_models.TourViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val preferencesViewModel: PreferencesViewModel by activityViewModels()
    private val tourViewModel: TourViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var closeExperiencesAdapter: CloseExperiencesAdapter
    private lateinit var previousExperiencesAdapter: PreviousExperiencesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initCloseExperiences()
        initPreviousExperiences()
        binding.btnSupport.setOnClickListener {
            sendHelpEmail()
        }
    }

    private fun initCloseExperiences() {
        initCloseExperiencesAdapter()
        observeCloseExperiencesMutableData()
        tourViewModel.getCloseExperiences()
        initHomeSwipeGif()
        binding.btnMoreExperiences.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_tourListFragment)
        }
    }

    private fun initCloseExperiencesAdapter() {
        closeExperiencesAdapter = CloseExperiencesAdapter { navigateToTourDetail(it) }
        binding.rvCloseExperiences.setHasFixedSize(true)
        binding.rvCloseExperiences.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCloseExperiences.adapter = closeExperiencesAdapter
    }

    private fun observeCloseExperiencesMutableData() {
        tourViewModel.closeExperiencesAreLoading.observe(viewLifecycleOwner, Observer {
            binding.pbCloseExperiences.isVisible = it
            binding.rvCloseExperiences.isVisible = !it
        })
        tourViewModel.closeExperiencesModel.observe(viewLifecycleOwner, Observer {
            closeExperiencesAdapter.updateList(it)
        })
    }

    private fun initHomeSwipeGif() {
        observeHomeSwipe()
        binding.rvCloseExperiences.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0 && binding.gviSwipe.isVisible) {
                    binding.gviSwipe.isVisible = false
                    CoroutineScope(Dispatchers.IO).launch {
                        preferencesViewModel.saveHomeSwipeSetting(false)
                    }
                }
            }
        })
    }

    private fun observeHomeSwipe() {
        preferencesViewModel.isHomeSwipeVisible.observe(viewLifecycleOwner) {
            binding.gviSwipe.isVisible = it
        }
    }


    private fun initPreviousExperiences() {
        initPreviousExperiencesAdapter()
        observePreviousExperiencesMutableData()
        tourViewModel.getPreviousExperiences()
    }

    private fun initPreviousExperiencesAdapter() {
        previousExperiencesAdapter = PreviousExperiencesAdapter { navigateToTourDetail(it) }
        binding.rvPreviousExperiences.setHasFixedSize(true)
        binding.rvPreviousExperiences.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPreviousExperiences.adapter = previousExperiencesAdapter
    }

    private fun observePreviousExperiencesMutableData() {
        tourViewModel.previousExperiencesAreLoading.observe(viewLifecycleOwner, Observer {
            binding.pbPreviousExperiences.isVisible = it
            binding.rvPreviousExperiences.isVisible = !it
        })
        tourViewModel.previousExperiencesModel.observe(viewLifecycleOwner, Observer {
            previousExperiencesAdapter.updateList(it)
        })
    }

    private fun navigateToTourDetail(id: String) {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_navigation_home_to_tourDetail, bundle)
    }

    private fun sendHelpEmail() {
        val sentTo = "historyar.support@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$sentTo")
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}