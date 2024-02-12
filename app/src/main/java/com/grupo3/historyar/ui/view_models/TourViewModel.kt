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
    val previousExperiencesModel = MutableLiveData<List<Tour>>()
    val allToursModel = MutableLiveData<List<Tour>>()
    val tourIsLoading = MutableLiveData<Boolean>()
    val closeExperiencesAreLoading = MutableLiveData<Boolean>()
    val previousExperiencesAreLoading = MutableLiveData<Boolean>()
    val allToursAreLoading = MutableLiveData<Boolean>()

    fun getTour(id: String) {
        viewModelScope.launch {
            tourIsLoading.postValue(true)
            val tour = tourRepository.getTourById(id)
            if (tour != null) {
                tourModel.postValue(tour!!)
            }
            tourIsLoading.postValue(false)
        }
    }

    fun getCloseExperiences() {
        viewModelScope.launch {
            closeExperiencesAreLoading.postValue(true)
            val tourList = tourRepository.getCloseExperiences()
            if (tourList != null) {
                closeExperiencesModel.postValue(tourList!!)
            } else {
                closeExperiencesModel.postValue(emptyList())
            }
            closeExperiencesAreLoading.postValue(false)
        }
    }

    fun getPreviousExperiences() {
        viewModelScope.launch {
            previousExperiencesAreLoading.postValue(true)
            val tourList = tourRepository.getPreviousExperiences()
            if (tourList != null) {
                previousExperiencesModel.postValue(tourList!!)
            } else {
                previousExperiencesModel.postValue(emptyList())
            }
            previousExperiencesAreLoading.postValue(false)
        }
    }

    fun getAll() {
        viewModelScope.launch {
            allToursAreLoading.postValue(true)
            val tourList = tourRepository.getAll()
            if (tourList != null) {
                allToursModel.postValue(tourList!!)
            } else {
                allToursModel.postValue(emptyList())
            }
            allToursAreLoading.postValue(false)
        }
    }
}