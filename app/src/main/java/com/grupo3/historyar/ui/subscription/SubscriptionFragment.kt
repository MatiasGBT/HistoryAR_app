package com.grupo3.historyar.ui.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : Fragment() {
    private lateinit var viewModel: SubscriptionViewModel
    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        initUI()
        return inflater.inflate(R.layout.fragment_subscription, container, false)
    }

    private fun initUI() {
        //TODO: Obtener datos de suscripción y mostrarlos debidamente
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}