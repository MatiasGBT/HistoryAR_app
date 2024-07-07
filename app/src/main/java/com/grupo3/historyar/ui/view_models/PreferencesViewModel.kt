package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grupo3.historyar.data.repositories.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(private val preferencesRepository: PreferencesRepository): ViewModel() {
    val isDarkModeEnabled = MutableLiveData<Boolean>()
    var darkModeSwitchMustBeEnabled: Boolean = false
    val isHomeSwipeVisible = MutableLiveData<Boolean>()

    fun initPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            preferencesRepository.getPreferences().collect { preferencesModel ->
                toggleDarkMode(preferencesModel.darkMode)
                toggleHomeSwipe(preferencesModel.homeSwipe)
            }
        }
    }

    fun toggleDarkMode(isDarkModeEnabled: Boolean) {
        this.isDarkModeEnabled.postValue(isDarkModeEnabled)
        darkModeSwitchMustBeEnabled = isDarkModeEnabled
    }

    suspend fun saveDarkModeSetting(isDarkModeEnabled: Boolean) {
        preferencesRepository.saveDarkModeSetting(isDarkModeEnabled)
    }

    private fun toggleHomeSwipe(isHomeSwipeVisible: Boolean) {
        this.isHomeSwipeVisible.postValue(isHomeSwipeVisible)
    }

    suspend fun saveHomeSwipeSetting(isHomeSwipeVisible: Boolean) {
        preferencesRepository.saveHomeSwipeSetting(isHomeSwipeVisible)
    }
}