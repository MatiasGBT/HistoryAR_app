package com.grupo3.historyar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.grupo3.historyar.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): UserEntity?

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteUser()
}