package com.grupo3.historyar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.historyar.adapters.CloseExperiencesAdapter
import com.grupo3.historyar.databinding.FragmentHomeBinding
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
    }

    private fun initCloseExperiences() {
        initCloseExperiencesAdapter()
        observeCloseExperiencesMutableData()
        tourViewModel.getCloseExperiences()
        initHomeSwipeGif()
    }

    private fun initCloseExperiencesAdapter() {
        closeExperiencesAdapter = CloseExperiencesAdapter { navigateToTourDetail(it) }
        binding.rvCloseExperiences.setHasFixedSize(true)
        binding.rvCloseExperiences.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCloseExperiences.adapter = closeExperiencesAdapter
    }

    private fun observeCloseExperiencesMutableData() {
        tourViewModel.isLoading.observe(viewLifecycleOwner, Observer {
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

    private fun navigateToTourDetail(id: String) {
        //TODO: Implementar navegar al detalle del tour
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}