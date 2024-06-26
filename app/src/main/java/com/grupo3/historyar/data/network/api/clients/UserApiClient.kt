package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiClient {

    @GET("usuario/usuarios/{id}")
    suspend fun getById(@Path("id") id: String): Response<UserModel>

    @POST("usuario/usuarios/")
    suspend fun saveUser(@Body user: UserModel): Response<UserModel>
}