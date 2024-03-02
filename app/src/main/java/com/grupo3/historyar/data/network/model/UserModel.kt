package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.models.User

data class UserModel(
    @SerializedName("uuid") val id: String = "",
    @SerializedName("foto") val photo: String = "",
    @SerializedName("nombreCompleto") val fullName: String = "",
    @SerializedName("recorridoFavorito") val favoriteTour: TourModel = TourModel(),
    @SerializedName("ultimosRecorridos") val lastTours: List<TourModel> = emptyList()
)

fun UserModel.toDomain() = User(
    id = id,
    photo = photo,
    fullName = fullName
    //favoriteTour = favoriteTour.toDomain(false),
)