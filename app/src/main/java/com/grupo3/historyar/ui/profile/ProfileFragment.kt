package com.grupo3.historyar.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.databinding.FragmentProfileBinding
import com.grupo3.historyar.ui.LoginActivity
import com.grupo3.historyar.ui.UserViewModel
import com.grupo3.historyar.ui.view_models.PreferencesViewModel


class ProfileFragment() : Fragment() {
    private val preferencesViewModel: PreferencesViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        initUI()
        return binding.root
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private fun initUI() {
        binding.switchDarkMode.isChecked = preferencesViewModel.darkModeSwitchMustBeEnabled
        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            preferencesViewModel.toggleDarkMode(value)
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            userViewModel.deleteUser()
            startActivity(Intent(this.activity, LoginActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}