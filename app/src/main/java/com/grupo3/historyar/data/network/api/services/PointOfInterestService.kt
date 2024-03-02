package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.PointOfInterestApiClient
import com.grupo3.historyar.data.network.model.PointOfInterestModel
import com.grupo3.historyar.data.network.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointOfInterestService @Inject constructor(private val api: PointOfInterestApiClient) {

    suspend fun getPointById(id: String): PointOfInterestModel {
        return withContext(Dispatchers.IO) {
            val response = api.getById(id)
            response.body() ?: PointOfInterestModel()
        }
    }
}