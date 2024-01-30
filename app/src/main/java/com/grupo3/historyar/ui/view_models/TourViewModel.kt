package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.repositories.PreferencesRepository
import com.grupo3.historyar.data.repositories.TourRepository
import com.grupo3.historyar.models.Tour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourViewModel @Inject constructor(private val tourRepository: TourRepository): ViewModel() {
    val tourModel = MutableLiveData<Tour>()
    val closeExperiencesModel = MutableLiveData<List<Tour>>()
    val isLoading = MutableLiveData<Boolean>()

    fun getTour(id: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val tour = tourRepository.getTourById(id)
            if (tour != null) {
                tourModel.postValue(tour!!)
            }
            isLoading.postValue(false)
        }
    }

    fun getCloseExperiences() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val tourList = tourRepository.getCloseExperiences()
            if (tourList != null) {
                closeExperiencesModel.postValue(tourList!!)
            }
            isLoading.postValue(false)
        }
    }
}