package com.grupo3.historyar.data.network.api.clients

import com.grupo3.historyar.data.network.model.UserModel
import com.grupo3.historyar.data.network.model.UserStateModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiClient {

    @GET("usuario/usuarios/{id}")
    suspend fun getById(@Path("id") id: String): Response<UserModel>

    @GET("usuario/usuarios/")
    suspend fun getAll(): Response<List<UserModel>>

    @POST("usuario/usuarios/")
    suspend fun saveUser(@Body user: UserModel): Response<UserModel>

    @PUT("usuario/estado_usuario/{id}/")
    suspend fun updateUserState(
        @Path("id") idUser: String,
        @Body userState: UserStateModel
    ): Response<UserModel>
}