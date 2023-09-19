package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreferencesViewModel : ViewModel() {
    val isDarkModeEnabled = MutableLiveData<Boolean>()
    var darkModeSwitchMustBeEnabled: Boolean = false

    fun toggleDarkMode(isDarkModeEnabled: Boolean) {
        this.isDarkModeEnabled.postValue(isDarkModeEnabled)
    }
}