package com.grupo3.historyar.di

import android.content.Context
import androidx.room.Room
import com.grupo3.historyar.data.database.HistoryARDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val DATABASE_NAME = "historyar_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HistoryARDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUserDao(db: HistoryARDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideTourDao(db: HistoryARDatabase) = db.getTourDao()

    @Singleton
    @Provides
    fun providePointDao(db: HistoryARDatabase) = db.getPointDao()
}