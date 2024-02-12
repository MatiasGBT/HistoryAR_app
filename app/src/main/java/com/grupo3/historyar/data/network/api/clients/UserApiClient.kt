package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface UserApiClient {

    @GET("/.json")
    suspend fun getById(id: String): Response<UserModel>
}