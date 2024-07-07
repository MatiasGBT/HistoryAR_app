package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.SubscriptionModel
import retrofit2.Response
import retrofit2.http.GET

interface SubscriptionApiClient {

    @GET("transaccion/")
    suspend fun getAll(): Response<List<SubscriptionModel>>
}