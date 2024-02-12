package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.TourModel
import retrofit2.Response
import retrofit2.http.GET

interface TourApiClient {

    @GET("/.json")
    suspend fun getById(id: String): Response<TourModel>

    @GET("/.json")
    suspend fun getCloseExperiences(): Response<List<TourModel>>

    @GET("/.json")
    suspend fun getPreviousExperiences(): Response<List<TourModel>>

    @GET("/.json")
    suspend fun getAll(): Response<List<TourModel>>
}