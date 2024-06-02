package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.RouteApiClient
import com.grupo3.historyar.data.network.model.RouteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RouteService @Inject constructor(private val api: RouteApiClient) {

    suspend fun getRoute(startPoint: String, endPoint: String): RouteModel {
        return withContext(Dispatchers.IO) {
            val response = api.getRoute(
                "5b3ce3597851110001cf62480002b680c4544383a0bcc8313394c20d",
                startPoint,
                endPoint
            )
            response.body() ?: RouteModel()
        }
    }
}