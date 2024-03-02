package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.PointOfInterestModel
import retrofit2.Response
import retrofit2.http.GET

interface PointOfInterestApiClient {

    @GET("/.json")
    suspend fun getById(id: String): Response<PointOfInterestModel>
}