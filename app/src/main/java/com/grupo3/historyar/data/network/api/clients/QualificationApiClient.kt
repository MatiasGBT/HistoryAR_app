package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.QualificationModel
import retrofit2.Response
import retrofit2.http.*

interface QualificationApiClient {

    @GET("calificacion")
    suspend fun getByTourId(): Response<List<QualificationModel>>

    @POST("calificacion/")
    suspend fun save(@Body qualification: QualificationModel): Response<QualificationModel>
}