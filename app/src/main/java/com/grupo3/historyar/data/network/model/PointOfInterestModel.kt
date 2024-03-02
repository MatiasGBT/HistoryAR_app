package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.models.PointOfInterest

data class PointOfInterestModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("nombre") val name: String = "",
    @SerializedName("imagen") val image: String = "",
    @SerializedName("latitud") val latitude: String = "",
    @SerializedName("longitud") val longitude: String = "",
    @SerializedName("modelo") val model: String = ""
)

fun PointOfInterestModel.toDomain() = PointOfInterest(
    id = id,
    name = name,
    image = image,
    latitude = latitude,
    longitude = longitude,
    model = model
)