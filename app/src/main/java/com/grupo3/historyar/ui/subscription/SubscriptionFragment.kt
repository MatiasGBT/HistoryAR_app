package com.grupo3.historyar.ui.subscription

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.grupo3.historyar.databinding.FragmentSubscriptionBinding
import com.grupo3.historyar.ui.SubActivity
import com.grupo3.historyar.ui.view_models.SubscriptionViewModel

class SubscriptionFragment : Fragment() {
    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!
    private val subscriptionViewModel: SubscriptionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        initViewModel()
        initUI()
        return binding.root
    }

    private fun initViewModel() {
        //TODO: Obtener datos de suscripción y mostrarlos debidamente. Filtrar por fecha
        subscriptionViewModel.subModel.observe(viewLifecycleOwner) {
            if (it.id != "" && it.isSubValid) {
                binding.btnSub.isVisible = false
                binding.tvSubStatus.text = "Tu suscripción esta activa, ¿qué esperas para darte un paseo 🚶‍♂️"
                binding.tvSubUntil.isVisible = true
                binding.tvSubUntil.text = "Te quedan ${it.daysUntilMonth} días de suscripción"
            }
        }
        subscriptionViewModel.getUserSubscription()
    }

    private fun initUI() {
        binding.btnSub.setOnClickListener {
            val intent = Intent(activity, SubActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}