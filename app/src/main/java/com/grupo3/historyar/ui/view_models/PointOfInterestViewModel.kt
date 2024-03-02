package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.repositories.PointOfInterestRepository
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.models.Tour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointOfInterestViewModel @Inject constructor(private val pointRepository: PointOfInterestRepository): ViewModel() {
    val pointModel = MutableLiveData<PointOfInterest>()
    val pointIsLoading = MutableLiveData<Boolean>()
    val pointListModel = MutableLiveData<List<PointOfInterest>>()
    val pointsAreLoading = MutableLiveData<Boolean>()

    fun getPoint(id: String) {
        viewModelScope.launch {
            pointIsLoading.postValue(true)
            val point = pointRepository.getPointById(id)
            if (point != null) {
                pointModel.postValue(point!!)
            }
            pointIsLoading.postValue(false)
        }
    }

    fun getPointsByTourId(idTour: String) {
        viewModelScope.launch {
            pointsAreLoading.postValue(true)
            val pointList = pointRepository.getPointsByTourId(idTour)
            if (pointList != null) {
                pointListModel.postValue(pointList!!)
            } else {
                pointListModel.postValue(emptyList())
            }
            pointsAreLoading.postValue(false)
        }
    }
}