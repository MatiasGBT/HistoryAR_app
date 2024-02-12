package com.grupo3.historyar.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.databinding.FragmentProfileBinding
import com.grupo3.historyar.ui.LoginActivity
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.tour_mini.TourMiniFragment
import com.grupo3.historyar.ui.view_models.UserViewModel
import com.grupo3.historyar.ui.view_models.PreferencesViewModel
import com.grupo3.historyar.ui.view_models.TourViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment() : Fragment() {
    private val preferencesViewModel: PreferencesViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val tourViewModel: TourViewModel by activityViewModels()
    private lateinit var auth : FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.switchDarkMode.isChecked = preferencesViewModel.darkModeSwitchMustBeEnabled
        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            preferencesViewModel.toggleDarkMode(value)
            CoroutineScope(Dispatchers.IO).launch {
                preferencesViewModel.saveDarkModeSetting(value)
            }
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            userViewModel.deleteUser()
            startActivity(Intent(this.activity, LoginActivity::class.java))
        }
        initUser()
        initFavoriteTourFragment()
    }

    private fun initUser() {
        userViewModel.userModel.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.photo).into(binding.ivAccountImage)
            binding.tvUsername.text = it.fullName
        })
        userViewModel.getUserLoggedIn()
    }

    private fun initFavoriteTourFragment() {
        tourViewModel.tourIsLoading.observe(viewLifecycleOwner, Observer {
            binding.pbTour.isVisible = it
            binding.fcFavoriteTour.isVisible = !it
        })
        val bundle = bundleOf(ID_BUNDLE to "1") //Se debe pasar el ID deseado. Debo obtener el ID por el favoriteTour del user
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<TourMiniFragment>(binding.fcFavoriteTour.id, args = bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}