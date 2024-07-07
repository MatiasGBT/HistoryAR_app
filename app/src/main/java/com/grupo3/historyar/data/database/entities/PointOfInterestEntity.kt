package com.grupo3.historyar.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grupo3.historyar.models.PointOfInterest

@Entity(tableName = "points")
data class PointOfInterestEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "model") val model: String
)

fun PointOfInterestEntity.toDomain() = PointOfInterest(id = id, name = name, image = image, latitude = latitude, longitude = longitude, model = model)