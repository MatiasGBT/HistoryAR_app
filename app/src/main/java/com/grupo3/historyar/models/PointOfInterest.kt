package com.grupo3.historyar.models

import com.grupo3.historyar.data.database.entities.PointOfInterestEntity

data class PointOfInterest(
    val id: String,
    val name: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    val model: String
)

fun PointOfInterestEntity.toDomain() = PointOfInterest(id = id, name = name, image = image, latitude = latitude, longitude = longitude, model = model)