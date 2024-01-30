package com.grupo3.historyar.data.repositories

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.grupo3.historyar.models.PreferencesModel
import com.grupo3.historyar.ui.dataStore
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val application: Application
) {
    companion object {
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_HOME_SWIPE = "key_home_swipe"
    }

    fun getPreferences(): Flow<PreferencesModel> {
        return application.dataStore.data.map { preferences ->
            PreferencesModel(
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                homeSwipe = preferences[booleanPreferencesKey(KEY_HOME_SWIPE)] ?: true
            )
        }
    }

    suspend fun saveDarkModeSetting(isDarkModeEnabled: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_DARK_MODE)] = isDarkModeEnabled
        }
    }

    suspend fun saveHomeSwipeSetting(isHomeSwipeVisible: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_HOME_SWIPE)] = isHomeSwipeVisible
        }
    }
}