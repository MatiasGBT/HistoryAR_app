package com.grupo3.historyar.ui.tour_play

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.grupo3.historyar.R
import com.grupo3.historyar.data.network.model.RouteModel
import com.grupo3.historyar.databinding.FragmentTourPlayBinding
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.ui.view_models.RouteViewModel
import com.grupo3.historyar.ui.view_models.SubscriptionViewModel
import com.grupo3.historyar.ui.view_models.TourViewModel
import com.grupo3.historyar.ui.view_models.UserViewModel

const val ID_BUNDLE = "id_bundle"

class TourPlayFragment : Fragment() {
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private val tourViewModel: TourViewModel by activityViewModels()
    private val routeViewModel: RouteViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val subscriptionViewModel: SubscriptionViewModel by activityViewModels()
    private var id: String? = null
    private var _binding: FragmentTourPlayBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var tour: Tour
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var pointList: List<PointOfInterest>

    private val mapReadyCallback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.setMinZoomPreference(15F)
        map.setMaxZoomPreference(20F)
        enableGoogleMapLocation()
        observeSubscription()
        observeRoute()
        subscriptionViewModel.getUserSubscription()
    }

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
        _binding = FragmentTourPlayBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.fabCamera.setOnClickListener {
            navigateToCamera()
        }
    }

    private fun observeSubscription() {
        subscriptionViewModel.subModel.observe(viewLifecycleOwner) {
            if (!it.isSubValid) {
                Toast.makeText(context, "No podes acceder a este contenido sin una suscripción", Toast.LENGTH_LONG).show()
                val bundle = bundleOf(com.grupo3.historyar.ui.tour_detail.ID_BUNDLE to id)
                findNavController().navigate(R.id.action_tourPlayFragment_to_navigation_home, bundle)
            } else {
                observeTour()
                observeUser()
                tourViewModel.getTour(id!!)
            }
        }
    }

    private fun observeTour() {
        tourViewModel.tourModel.observe(viewLifecycleOwner) {
            tour = it
            binding.tvTourName.text = tour.name
            initPointList(tour.points)
            userViewModel.getUserLoggedIn()
            getCurrentLocation()
        }
    }

    private fun observeUser() {
        userViewModel.userModel.observe(viewLifecycleOwner) {
            if (this::tour.isInitialized && !it.lastTourIds.contains(tour.id)) {
                val list = it.lastTourIds.toMutableList()
                list.add(tour.id)
                it.lastTourIds = list
                userViewModel.updateUser(it)
            }
        }
    }

    private fun initPointList(points: List<PointOfInterest>) {
        pointList = points
        var nextIndex: Int;
        points.forEachIndexed { currentIndex, point ->
            val markerLocation = LatLng(point.latitude.toDouble(), point.longitude.toDouble())
            map.addMarker(MarkerOptions().position(markerLocation).title(point.name))
            nextIndex = currentIndex + 1
            if (nextIndex in points.indices) {
                createRouteBetweenPOIs(points[currentIndex], points[nextIndex])
            }
        }
    }

    private fun createRouteBetweenCurrentLocationAndFirstPOI(currentLocation: Location) {
        val origin = currentLocation.longitude.toString() + ',' + currentLocation.latitude.toString()
        val destination = pointList.first().longitude + ',' + pointList.first().latitude
        routeViewModel.getRoute(origin, destination)
    }

    private fun createRouteBetweenPOIs(originPoint: PointOfInterest, destinationPoint: PointOfInterest) {
        val origin = originPoint.longitude + ',' + originPoint.latitude
        val destination = destinationPoint.longitude + ',' + destinationPoint.latitude
        routeViewModel.getRoute(origin, destination)
    }

    private fun observeRoute() {
        routeViewModel.routeModel.observe(viewLifecycleOwner) {
            drawRouteBetweenPoints(it)
        }
    }

    private fun navigateToCamera() {
        val bundle = bundleOf(ID_BUNDLE to id)
        findNavController().navigate(R.id.action_tourPlayFragment_to_cameraFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    createRouteBetweenCurrentLocationAndFirstPOI(location)
                    centerMapToCurrentLocation(location)
                }
            }
    }

    private fun centerMapToCurrentLocation(location: Location) {
        val currentLocation = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableGoogleMapLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireActivity(),
                "La ubicación no esta activada para esta aplicación. Por favor, activala en los ajustes",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    requireActivity(),
                    "La ubicación no esta activada para esta aplicación. Por favor, activala en los ajustes",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    //TODO: Reemplazar RouteModel por su contraparte en modelo estandar
    private fun drawRouteBetweenPoints(route: RouteModel) {
        val polylineOptions = PolylineOptions()
        route.features.first().geometry.coordinates.forEach {
            polylineOptions.add(LatLng(it[1], it[0]))
        }
        polylineOptions.color(Color.BLUE)
        map.addPolyline(polylineOptions)
    }

    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                requireActivity(),
                "La ubicación no esta activada para esta aplicación. Por favor, activala en los ajustes",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}