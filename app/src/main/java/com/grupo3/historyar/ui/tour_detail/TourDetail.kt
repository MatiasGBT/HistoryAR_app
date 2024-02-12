package com.grupo3.historyar.ui.tour_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentTourDetailBinding

const val ID_BUNDLE = "id_bundle"
class TourDetail : Fragment() {
    private var id: String? = null
    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        initUI()
        return inflater.inflate(R.layout.fragment_tour_detail, container, false)
    }

    private fun initUI() {
        //No se cambia el texto pero el ID si existe, revisar
        binding.tvTest.text = id
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}