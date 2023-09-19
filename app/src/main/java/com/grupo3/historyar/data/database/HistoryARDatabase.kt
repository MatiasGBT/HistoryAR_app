package com.grupo3.historyar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class HistoryARDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
}