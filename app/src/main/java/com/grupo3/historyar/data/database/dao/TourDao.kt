package com.grupo3.historyar.data.database.dao

import androidx.room.*
import com.grupo3.historyar.data.database.entities.TourEntity
import javax.inject.Inject

@Dao
interface TourDao {

    @Query("SELECT * FROM tours WHERE id = :id")
    suspend fun getTourById(id: String): TourEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTour(tour: TourEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourList(tourList: List<TourEntity>)

    @Query("DELETE FROM tours WHERE is_favorite")
    suspend fun deleteFavoriteTour()
}