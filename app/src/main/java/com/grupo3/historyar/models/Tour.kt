package com.grupo3.historyar.models

import com.grupo3.historyar.data.database.entities.TourEntity

data class Tour(
    val id: String,
    val name: String,
    val description: String,
    val duration: Int,
    val image: String,
    val pointsOfInterest: List<PointOfInterest>,
    val isFavorite: Boolean
)

fun TourEntity.toDomain() = Tour(id = id, name = name, description = description, duration = duration, image = image, pointsOfInterest = listOf(), isFavorite = isFavorite)