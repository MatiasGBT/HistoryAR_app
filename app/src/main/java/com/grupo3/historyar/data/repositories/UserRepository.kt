package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getCurrentUser(): UserEntity? {
        return userDao.getCurrentUser()
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser() {
        userDao.deleteUser()
    }
}