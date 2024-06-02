package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.UserApiClient
import com.grupo3.historyar.data.network.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val api: UserApiClient) {

    suspend fun getUserById(id: String): UserModel {
        return withContext(Dispatchers.IO) {
            val response = api.getById(id)
            response.body() ?: UserModel()
        }
    }

    suspend fun saveUser(user: UserModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = api.saveUser(user)
            response.body() ?: UserModel()
        }
    }
}