package com.grupo3.historyar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grupo3.historyar.data.database.dao.*
import com.grupo3.historyar.data.database.entities.*

@Database(entities = [UserEntity::class, TourEntity::class, PointOfInterestEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryARDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getTourDao(): TourDao

    abstract fun getPointDao(): PointOfInterestDao
}