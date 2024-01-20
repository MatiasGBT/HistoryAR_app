package com.grupo3.historyar.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tours")
data class TourEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
)