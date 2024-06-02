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
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.grupo3.historyar.R
import com.grupo3.historyar.adapters.CloseExperiencesAdapter
import com.grupo3.historyar.adapters.PreviousExperiencesAdapter
import com.grupo3.historyar.databinding.FragmentHomeBinding
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val preferencesViewModel: PreferencesViewModel by activityViewModels()
    private val tourViewModel: TourViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var closeExperiencesAdapter: CloseExperiencesAdapter
    private lateinit var previousExperiencesAdapter: PreviousExperiencesAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted)
            getLastLocation()
        else
            showNoLocationOnCloseExperiences()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        initUI()
        return binding.root
    }

    private fun initUI() {
        if (isLocationEnabled())
            startLocationPermissionRequest()
        else
            showNoLocationOnCloseExperiences()
        initUser()
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
                if (location != null) {
                    initCloseExperiences(location)
                } else {
                    Log.i("HistoryAR.debug", "No last known location. Fetching current location.")
                    getCurrentLocation()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location  != null) {
                    initCloseExperiences(location)
                } else {
                    Log.i("HistoryAR.debug", "No current location found.")
                    showNoLocationOnCloseExperiences()
                }
            }
    }

    private fun showNoLocationOnCloseExperiences() {
        binding.rvCloseExperiences.isVisible = false
        binding.gviSwipe.isVisible = false
        binding.btnMoreExperiences.isVisible = false
        binding.tvNoGeo.isVisible = true
    }

    private fun initCloseExperiences(location: Location) {
        initCloseExperiencesAdapter(location)
        observeCloseExperiencesMutableData()
        tourViewModel.getCloseExperiences(location.latitude, location.longitude)
        initHomeSwipeGif()
        binding.btnMoreExperiences.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_tourListFragment)
        }
    }

    private fun initCloseExperiencesAdapter(location: Location) {
        closeExperiencesAdapter = CloseExperiencesAdapter(
            onItemSelected = { navigateToTourDetail(it) },
            onPlaySelected = { navigateToTourPlay(it) },
            onFavSelected = { navigateToTourDetail(it) }, //TODO: Darle a favorito
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
            if (it.isNotEmpty()) {
                closeExperiencesAdapter.updateList(it)
                binding.tvNoGeo.isVisible = false
            } else {
                showNoCloseExperiences()
            }
        }
    }

    private fun showNoCloseExperiences() {
        binding.rvCloseExperiences.isVisible = false
        binding.gviSwipe.isVisible = false
        binding.tvNoCloseExperiences.isVisible = true
        binding.btnMoreExperiences.isVisible = true
        binding.tvNoGeo.isVisible = false
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

    private fun initUser() {
        userViewModel.userModel.observe(viewLifecycleOwner, Observer {
            initPreviousExperiences(it.lastTourIds)
        })
        userViewModel.getUserLoggedIn()
    }

    private fun initPreviousExperiences(lastTourIds: List<String>) {
        initPreviousExperiencesAdapter()
        observePreviousExperiencesMutableData()
        tourViewModel.getPreviousExperiences(lastTourIds)
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
            if (it.isNotEmpty())
                previousExperiencesAdapter.updateList(it)
            else
                showNoPreviousExperiences()
        })
    }

    private fun showNoPreviousExperiences() {
        binding.rvPreviousExperiences.isVisible = false
        binding.tvNoPreviousExperiences.isVisible = true
    }

    private fun navigateToTourDetail(id: String) {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_navigation_home_to_tourDetail, bundle)
    }

    private fun navigateToTourPlay(id: String) {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_navigation_home_to_tourPlayFragment, bundle)
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

    private fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}