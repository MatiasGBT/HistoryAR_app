package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("uuid") val id: String = "",
    @SerializedName("foto") val photo: String = "",
    @SerializedName("recorridoFavorito") val favoriteTour: TourModel = TourModel(),
    @SerializedName("ultimosRecorridos") val lastTours: List<TourModel> = emptyList()
)
