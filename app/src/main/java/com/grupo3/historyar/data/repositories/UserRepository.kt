package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.toDomain
import com.grupo3.historyar.data.network.api.services.UserService
import com.grupo3.historyar.data.network.model.toDatabase
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.User
import com.grupo3.historyar.models.toModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val userService: UserService
) {

    suspend fun getCurrentUser(): User {
        return userDao.getCurrentUser()!!.toDomain()
    }

    suspend fun getUserById(idUser: String): User {
        return userService.getUserById(idUser).toDomain()
    }

    suspend fun saveUser(user: User) {
        var userModel = userService.getUserById(user.id)
        if (userModel.id.isEmpty()) {
            userModel = userService.saveUser(user.toModel())
        }
        userDao.deleteUser()
        userDao.insertUser(userModel.toDatabase())
    }

    suspend fun deleteUser() {
        userDao.deleteUser()
    }
}