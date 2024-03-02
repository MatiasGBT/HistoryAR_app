package com.grupo3.historyar.data.database.dao

import androidx.room.*
import com.grupo3.historyar.data.database.entities.PointOfInterestEntity

@Dao
interface PointOfInterestDao {

    @Query("SELECT * FROM points WHERE id = :id")
    suspend fun getPointById(id: String): PointOfInterestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPointList(tourList: List<PointOfInterestEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: PointOfInterestEntity)
}