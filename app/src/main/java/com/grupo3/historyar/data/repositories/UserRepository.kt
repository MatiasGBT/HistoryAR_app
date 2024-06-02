package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.toDomain
import com.grupo3.historyar.data.network.api.services.UserService
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.User
import com.grupo3.historyar.models.toDatabase
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val userService: UserService
) {

    suspend fun getCurrentUser(): User {
        return User("2", "https://tesis-web.onrender.com/media/images/DALLE_2024-01-06_08.40.26_-_A_Shiba_Inu_dog_wearing_a_beret_and_black_turtleneck_wyIYbCi.png", "Usuario test")
        return userDao.getCurrentUser()!!.toDomain() //TODO: Devuelve null por alguna razón, quiza no llega a guardar? EDIT: La API no funciona
        //return userService.getUserById("2").toDomain()
    }

    suspend fun getUserById(idUser: String): User {
        return userService.getUserById(idUser).toDomain()
    }

    suspend fun saveUser(user: User) {
        var userModel = userService.getUserById(user.id)
        if (userModel.id.isEmpty()) {
            //userModel = userService.saveUser(user.toModel())
        }
        userDao.deleteUser()
        userDao.insertUser(user.toDatabase()) //TODO: Pasar userModel cuando funcione
    }

    suspend fun deleteUser() {
        userDao.deleteUser()
    }
}