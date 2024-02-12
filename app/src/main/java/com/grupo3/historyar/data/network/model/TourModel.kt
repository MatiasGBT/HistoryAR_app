package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.data.database.entities.TourEntity
import com.grupo3.historyar.models.Tour

data class TourModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("nombre") val name: String = "",
    @SerializedName("descripcion") val description: String = "",
    @SerializedName("duracion") val duration: Int = 0,
    @SerializedName("puntosDeInteres") val pointsOnInterest: List<PointOfInterestModel> = emptyList()
)

fun TourModel.toDatabase(isFavorite: Boolean) = TourEntity(
    id = id,
    name = name,
    description = description,
    duration = duration,
    image = pointsOnInterest[0].image,
    latitude = pointsOnInterest[0].latitude,
    longitude = pointsOnInterest[0].longitude,
    isFavorite = isFavorite
)

fun TourModel.toDomain(isFavorite: Boolean) = Tour(
    id = id,
    name = name,
    description = description,
    duration = duration,
    image = pointsOnInterest[0].image,
    pointsOfInterest = pointsOnInterest.map { it.toDomain() },
    isFavorite = isFavorite
)