package com.grupo3.historyar.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grupo3.historyar.models.Tour

@Entity(tableName = "tours")
data class TourEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "imagen") val image: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "points") val points: List<PointOfInterestEntity>
)

fun TourEntity.toDomain() = Tour(
    id = id,
    name = name,
    description = description,
    duration = duration,
    image = image,
    latitude = latitude,
    longitude = longitude,
    isFavorite = isFavorite,
    points = points.map { it.toDomain() }
)