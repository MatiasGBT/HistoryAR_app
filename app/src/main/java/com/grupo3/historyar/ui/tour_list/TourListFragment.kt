package com.grupo3.historyar.ui.tour_list

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTourListBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initUI()
        return binding.root
    }

    private fun initUI() {
        startLocationPermissionRequest()
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
                    initAllTours(location)
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
                    initAllTours(location)
                }
            }
    }

    private fun initAllTours(location: Location) {
        initAllToursAdapter(location)
        observeAllToursMutableData()
        tourViewModel.getAll()
    }

    private fun initAllToursAdapter(location: Location) {
        tourListAdapter = TourListAdapter(
            onItemSelected = { navigateToTourDetail(it) },
            onPlaySelected = { navigateToTourPlay(it) },
            onFavSelected = { navigateToTourDetail(it) }, //TODO: Darle a favorito
            currentUserLocation = location
        )
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

    private fun navigateToTourPlay(id: String) {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_tourListFragment_to_tourPlayFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}