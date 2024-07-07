package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.data.database.entities.TourEntity
import com.grupo3.historyar.models.Tour

data class TourModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("nombre") val name: String = "",
    @SerializedName("descripcion") val description: String = "",
    @SerializedName("duracion") val duration: String = "",
    @SerializedName("puntoInteres") val pointsOfInterest: List<PointOfInterestModel> = emptyList(),
    var isFavorite: Boolean = false
)

fun TourModel.toDatabase() = TourEntity(
    id = id,
    name = name,
    description = description,
    duration = duration,
    image = pointsOfInterest.first().image,
    latitude = pointsOfInterest.first().latitude,
    longitude = pointsOfInterest.first().longitude,
    isFavorite = isFavorite,
    points = pointsOfInterest.map { it.toDatabase() }
)

fun TourModel.toDomain() = Tour(
    id = id,
    name = name,
    description = description,
    duration = duration,
    image = pointsOfInterest.first().image,
    latitude = pointsOfInterest.first().latitude,
    longitude = pointsOfInterest.first().longitude,
    isFavorite = isFavorite,
    points = pointsOfInterest.map { it.toDomain() }
)