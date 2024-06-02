package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.QualificationApiClient
import com.grupo3.historyar.data.network.model.QualificationModel
import com.grupo3.historyar.models.Qualification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QualificationService @Inject constructor(private val api: QualificationApiClient) {

    suspend fun getByTourId(idTour: String): List<QualificationModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getByTourId()
            response.body()?.filter { it.idTour == idTour } ?: emptyList()
        }
    }

    suspend fun save(qualification: Qualification) {
        withContext(Dispatchers.IO) {
            api.save(qualification)
        }
    }

    suspend fun delete(id: String) {
        withContext(Dispatchers.IO) {
            api.delete(id)
        }
    }
}