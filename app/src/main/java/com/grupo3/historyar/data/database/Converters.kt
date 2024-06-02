package com.grupo3.historyar.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.grupo3.historyar.data.database.entities.PointOfInterestEntity
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromPointOfInterestList(points: List<PointOfInterestEntity>?): String? {
        if (points == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<PointOfInterestEntity>>() {}.type
        return gson.toJson(points, type)
    }

    @TypeConverter
    fun toPointOfInterestList(pointsString: String?): List<PointOfInterestEntity>? {
        if (pointsString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<PointOfInterestEntity>>() {}.type
        return gson.fromJson(pointsString, type)
    }
}