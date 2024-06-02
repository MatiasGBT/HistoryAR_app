package com.grupo3.historyar.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivityAppBinding
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
        initActionBar()
        initNavbar()
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