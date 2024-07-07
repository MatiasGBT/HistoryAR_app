package com.grupo3.historyar.ui.mini_point_of_interest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.grupo3.historyar.databinding.FragmentMiniPointOfInterestBinding
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.PointOfInterestViewModel

const val ID_BUNDLE = "id_bundle"
class MiniPointOfInterestFragment : Fragment() {
    private var id: String? = null
    private val pointViewModel: PointOfInterestViewModel by activityViewModels()
    private var _binding: FragmentMiniPointOfInterestBinding? = null
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
        _binding = FragmentMiniPointOfInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}