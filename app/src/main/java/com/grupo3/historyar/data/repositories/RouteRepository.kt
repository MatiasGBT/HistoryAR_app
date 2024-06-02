package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.network.api.services.RouteService
import com.grupo3.historyar.data.network.model.RouteModel
import javax.inject.Inject

class RouteRepository @Inject constructor(
    private val routeService: RouteService
) {

    suspend fun getRoute(startPoint: String, endPoint: String): RouteModel {
        return routeService.getRoute(startPoint, endPoint)
    }
}