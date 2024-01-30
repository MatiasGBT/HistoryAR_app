package com.grupo3.historyar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivityMainBinding
import com.grupo3.historyar.ui.view_models.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val preferencesViewModel: PreferencesViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        observeDarkMode()
        preferencesViewModel.initPreferences()
        if (isUserLoggedIn()) {
            initActionBar()
            initNavbar()
        } else {
            navigateToLoginActivity()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.actionBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initNavbar() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
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

    private suspend fun saveDarkModeSetting(isDarkModeEnabled: Boolean) {
        preferencesViewModel.saveDarkModeSetting(isDarkModeEnabled)
    }
}