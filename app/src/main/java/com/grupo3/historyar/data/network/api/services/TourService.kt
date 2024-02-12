package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.TourApiClient
import com.grupo3.historyar.data.network.model.TourModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TourService @Inject constructor(private val api: TourApiClient) {

    suspend fun getTourById(id: String): TourModel {
        return withContext(Dispatchers.IO) {
            val response = api.getById(id)
            response.body() ?: TourModel()
        }
    }

    suspend fun getCloseExperiences(): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getCloseExperiences()
            response.body() ?: emptyList()
        }
    }

    suspend fun getPreviousExperiences(): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getPreviousExperiences()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAll(): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAll()
            response.body() ?: emptyList()
        }
    }
}