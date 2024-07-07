package com.grupo3.historyar.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.gorisse.thomas.sceneform.scene.await
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentCameraBinding
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.ui.view_models.PointOfInterestViewModel
import com.grupo3.historyar.ui.view_models.TourViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

const val ID_BUNDLE = "id_bundle"
class CameraFragment : Fragment() {
    private val tourViewModel: TourViewModel by activityViewModels()
    private var id: String? = null
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var arFragment: ArFragment
    private val arSceneView get() = arFragment.arSceneView
    private val scene get() = arSceneView.scene
    //private var model: Renderable? = null
    //private var modelView: ViewRenderable? = null
    private lateinit var tour: Tour
    private lateinit var pointList: List<PointOfInterest>
    private var createdPointList: MutableList<PointOfInterest> = emptyList<PointOfInterest>().toMutableList()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation()
        } else {
            //TODO: Añadir una pantalla que le indique al usuario que los permisos son obligatorios
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(com.grupo3.historyar.ui.tour_detail.ID_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initAR()
        initUI()
        return binding.root
    }

    private fun initUI() {
        tourViewModel.tourModel.observe(viewLifecycleOwner) {
            tour = it
            pointList = it.points
            pointList.forEach { point ->
                CoroutineScope(Dispatchers.Main).launch {
                    loadModel(point)
                }
            }
        }
        tourViewModel.getTour(id!!)
        binding.fabMap.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        startLocationPermissionRequest()
    }

    private fun initAR() {
        arFragment = (childFragmentManager.findFragmentById(R.id.arFragment) as ArFragment).apply {
            setOnSessionConfigurationListener { session, config ->
                //TODO: Modify the AR session configuration here
            }
            setOnViewCreatedListener { arSceneView ->
                arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL)
            }
            setOnTapArPlaneListener(::onTapPlane)
        }
    }

    private suspend fun loadModel(point: PointOfInterest) {
        point.modelRenderable = ModelRenderable.builder()
            .setSource(context, Uri.parse(point.model.replace("http://","https://")))
            //.setSource(context, Uri.parse("models/halloween.glb"))
            .setAsyncLoadEnabled(true)
            .setIsFilamentGltf(true)
            .await()
        point.modelView = ViewRenderable.builder()
            .setView(context, R.layout.view_renderable_info)
            .await()
    }

    private fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        if (!::location.isInitialized || pointList.isEmpty())
            return

        for (it in pointList) {
            val pointLocation = Location("Point")
            pointLocation.latitude = it.latitude.toDouble()
            pointLocation.longitude = it.longitude.toDouble()
            val distance = location.distanceTo(pointLocation).roundToInt()

            if (distance <= 20 && !createdPointList.contains(it)) {
                if (it.modelRenderable == null || it.modelView == null) {
                    Toast.makeText(context, "Cargando modelo...", Toast.LENGTH_SHORT).show()
                    return
                }

                // Create the Anchor.
                scene.addChild(AnchorNode(hitResult.createAnchor()).apply {
                    // Create the transformable model and add it to the anchor.
                    addChild(TransformableNode(arFragment.transformationSystem).apply {
                        renderable = it.modelRenderable
                        renderableInstance.setCulling(false)
                        renderableInstance.animate(true).start()
                        localPosition = Vector3(0.0f, 0f, 0.0f)
                        localScale = Vector3(0.25f, 0.25f, 0.25f)
                        // Add the View
                        /*addChild(Node().apply {
                            // Define the relative position
                            localPosition = Vector3(0.0f, 1f, 0.0f)
                            localScale = Vector3(0.7f, 0.7f, 0.7f)
                            renderable = modelView
                        })*/
                    })
                })
                createdPointList.add(it)
                break
            }
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
                    this.location = location
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
                    this.location = location
                }
            }
    }
}