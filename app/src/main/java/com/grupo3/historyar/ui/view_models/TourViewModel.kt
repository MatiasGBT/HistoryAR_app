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
            tourModel.postValue(tour)
            tourIsLoading.postValue(false)
        }
    }

    fun getCloseExperiences(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            closeExperiencesAreLoading.postValue(true)
            val tourList = tourRepository.getCloseExperiences(latitude, longitude)
            closeExperiencesModel.postValue(tourList!!)
            closeExperiencesAreLoading.postValue(false)
        }
    }

    fun getPreviousExperiences(lastTourIds: List<String>) {
        viewModelScope.launch {
            previousExperiencesAreLoading.postValue(true)
            val tourList = tourRepository.getPreviousExperiences(lastTourIds)
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
            allToursModel.postValue(tourList)
            allToursAreLoading.postValue(false)
        }
    }

    fun setFavoriteTour(idUser: String, idTour: String) {
        viewModelScope.launch {
            tourRepository.setFavoriteTour(idUser, idTour)
        }
    }
}