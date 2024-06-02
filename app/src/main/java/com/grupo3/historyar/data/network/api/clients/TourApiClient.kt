package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.TourModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TourApiClient {

    @GET("recorrido/recorrido/{id}")
    suspend fun getById(@Path("id") id: String): Response<TourModel>

    @GET("recorrido/ubicacion_recorrido/")
    suspend fun getCloseExperiences(
        @Query("latitud") latitude: Double,
        @Query("longitud") longitude: Double
    ): Response<List<TourModel>>

    @GET("usuario/usuarios/{id}")
    suspend fun getPreviousExperiences(idUser: String): Response<List<TourModel>>

    @GET("recorrido/recorrido/")
    suspend fun getAll(): Response<List<TourModel>>
}