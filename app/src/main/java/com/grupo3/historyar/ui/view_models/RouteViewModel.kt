package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.network.model.RouteModel
import com.grupo3.historyar.data.repositories.PreferencesRepository
import com.grupo3.historyar.data.repositories.RouteRepository
import com.grupo3.historyar.data.repositories.TourRepository
import com.grupo3.historyar.models.Tour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(private val routeRepository: RouteRepository): ViewModel() {
    val routeModel = MutableLiveData<RouteModel>()

    fun getRoute(startPoint: String, endPoint: String) {
        viewModelScope.launch {
            val route = routeRepository.getRoute(startPoint, endPoint)
            routeModel.postValue(route)
        }
    }
}