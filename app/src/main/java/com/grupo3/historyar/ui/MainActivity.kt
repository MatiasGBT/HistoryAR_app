package com.grupo3.historyar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivityMainBinding
import com.grupo3.historyar.models.PreferencesModel
import com.grupo3.historyar.ui.view_models.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_DARK_MODE = "key_dark_mode"
    }

    private lateinit var binding: ActivityMainBinding
    private val preferencesViewModel: PreferencesViewModel by viewModels()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        initDarkMode()
        if (isUserLoggedIn()) {
            initActionBar()
            initNavbar()
            observeDarkMode()
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

    private fun initDarkMode() {
        CoroutineScope(Dispatchers.IO).launch {
            getPreferences().collect { preferencesModel ->
                toggleDarkMode(preferencesModel.darkMode)
                preferencesViewModel.darkModeSwitchMustBeEnabled = preferencesModel.darkMode
            }
        }
    }

    private fun getPreferences(): Flow<PreferencesModel> {
        return dataStore.data.map { preferences ->
            PreferencesModel(
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false
            )
        }
    }

    private fun toggleDarkMode(isDarkModeEnabled: Boolean) {
        if (isDarkModeEnabled)
            enableDarkMode()
        else
            disableDarkMode()
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun observeDarkMode() {
        preferencesViewModel.isDarkModeEnabled.observe(this) { isDarkModeEnabled ->
            toggleDarkMode(isDarkModeEnabled)
            CoroutineScope(Dispatchers.IO).launch {
                saveDarkModeSetting(isDarkModeEnabled)
            }
        }
    }

    private suspend fun saveDarkModeSetting(isDarkModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_DARK_MODE)] = isDarkModeEnabled
        }
    }
}