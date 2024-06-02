package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.RouteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteApiClient {

    @GET("/v2/directions/foot-walking")
    suspend fun getRoute(
        @Query("api_key") key: String,
        @Query("start", encoded = true) startPoint: String,
        @Query("end", encoded = true) endPoint: String
    ): Response<RouteModel>
}