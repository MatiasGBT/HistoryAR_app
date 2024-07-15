package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.UserApiClient
import com.grupo3.historyar.data.network.model.UserModel
import com.grupo3.historyar.data.network.model.UserStateModel
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
            val responseEmail = api.getAll()
            val users = responseEmail.body() ?: emptyList()
            var userToLogin = UserModel()
            var userToSignUp: UserModel
            users.forEach {
                if (it.email == user.email){
                    userToLogin = it
                }
            }
            if (userToLogin.email == "") {
                val responseUser = api.saveUser(user)
                userToSignUp = responseUser.body() ?: UserModel()
                userToSignUp
            } else {
                userToLogin
            }
        }
    }

    suspend fun updateUserState(idUser: String, userState: Boolean): UserModel {
        return withContext(Dispatchers.IO) {
            val userState = UserStateModel(isActive = userState)
            val response = api.updateUserState(idUser, userState)
            response.body() ?: UserModel()
        }
    }
}