package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.QualificationModel
import com.grupo3.historyar.models.Qualification
import retrofit2.Response
import retrofit2.http.*

interface QualificationApiClient {

    @GET("calificacion")
    suspend fun getByTourId(): Response<List<QualificationModel>>

    @POST("/.json")
    suspend fun save(qualification: Qualification)

    @DELETE("/.json")
    suspend fun delete(id: String)
}