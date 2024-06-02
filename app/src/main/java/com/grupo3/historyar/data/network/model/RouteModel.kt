package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName

data class RouteModel(
    @SerializedName("features") val features: List<Feature> = emptyList()
)

data class Feature(
    @SerializedName("geometry") val geometry: Geometry
)

data class Geometry(
    @SerializedName("coordinates") val coordinates: List<List<Double>>
)
