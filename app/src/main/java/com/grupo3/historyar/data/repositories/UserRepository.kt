package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.toDomain
import com.grupo3.historyar.data.network.api.services.UserService
import com.grupo3.historyar.data.network.model.toDatabase
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.User
import com.grupo3.historyar.models.toDatabase
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

    suspend fun saveUser(user: User): User {
        val userModel = userService.saveUser(user.toModel())
        if (userModel.isActive) {
            userDao.deleteUser()
            userDao.insertUser(userModel.toDatabase())
        }
        return userModel.toDomain()
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDatabase())
    }

    suspend fun deleteUser() {
        userDao.deleteUser()
    }

    suspend fun updateUserState(idUser: String, userState: Boolean) {
        userService.updateUserState(idUser, userState)
    }
}