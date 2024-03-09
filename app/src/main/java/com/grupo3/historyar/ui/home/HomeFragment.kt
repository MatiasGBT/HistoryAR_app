package com.grupo3.historyar.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.grupo3.historyar.R
import com.grupo3.historyar.adapters.CloseExperiencesAdapter
import com.grupo3.historyar.adapters.PreviousExperiencesAdapter
import com.grupo3.historyar.databinding.FragmentHomeBinding
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation()
        } else {
            //TODO: Añadir una pantalla que le indique al usuario que los permisos son obligatorios
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initUI()
        return binding.root
    }

    private fun initUI() {
        startLocationPermissionRequest()
        initPreviousExperiences()
        binding.btnSupport.setOnClickListener {
            sendHelpEmail()
        }
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    Log.i("HistoryAR", "No last known location. Fetching current location.")
                    getCurrentLocation()
                } else {
                    initCloseExperiences(location)
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    Log.i("test", "No current location.")
                    //TODO: Mostrar texto de que no se pudo obtener la ubicación
                } else {
                    initCloseExperiences(location)
                }
            }
    }

    private fun initCloseExperiences(location: Location) {
        initCloseExperiencesAdapter(location)
        observeCloseExperiencesMutableData()
        tourViewModel.getCloseExperiences()
        initHomeSwipeGif()
        binding.btnMoreExperiences.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_tourListFragment)
        }
    }

    private fun initCloseExperiencesAdapter(location: Location) {
        closeExperiencesAdapter = CloseExperiencesAdapter(
            onItemSelected = { navigateToTourDetail(it) },
            currentUserLocation = location
        )
        binding.rvCloseExperiences.setHasFixedSize(true)
        binding.rvCloseExperiences.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCloseExperiences.adapter = closeExperiencesAdapter
    }

    private fun observeCloseExperiencesMutableData() {
        tourViewModel.closeExperiencesAreLoading.observe(viewLifecycleOwner) {
            binding.pbCloseExperiences.isVisible = it
            binding.rvCloseExperiences.isVisible = !it
        }
        tourViewModel.closeExperiencesModel.observe(viewLifecycleOwner) {
            closeExperiencesAdapter.updateList(it)
        }
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