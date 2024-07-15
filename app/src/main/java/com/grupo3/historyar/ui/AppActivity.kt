package com.grupo3.historyar.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivityAppBinding
import com.grupo3.historyar.ui.tour_mini.ID_BUNDLE
import com.grupo3.historyar.ui.view_models.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private val preferencesViewModel: PreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeDarkMode()
        preferencesViewModel.initPreferences()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            val data: Uri? = intent.data
            data?.let {
                val tourId = it.lastPathSegment
                if (tourId != null) {
                    navigateToTourDetailFragment(tourId)
                }
            }
        }
    }

    private fun navigateToTourDetailFragment(tourId: String) {
        val bundle = bundleOf(ID_BUNDLE to tourId)
        findNavController(R.id.nav_host_fragment_activity_app).navigate(R.id.action_navigation_home_to_tourDetailFragment, bundle)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.actionBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initNavbar() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_app)
        navView.setupWithNavController(navController)
    }

    private fun observeDarkMode() {
        preferencesViewModel.isDarkModeEnabled.observe(this) {
            toggleDarkMode(it)
            initActionBar()
            initNavbar()
        }
    }

    private fun toggleDarkMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled)
            enableDarkMode()
        else
            disableDarkMode()
    }

    private fun enableDarkMode() {
        runOnUiThread {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun disableDarkMode() {
        runOnUiThread {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}