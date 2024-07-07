package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.models.User

data class UserModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("imagen") val photo: String = "",
    @SerializedName("nombre") val fullName: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("recorridoFavorito") val favoriteTourId: String? = "",
    @SerializedName("ultimosRecorridos") val lastTourIds: String = ""
)

fun UserModel.toDomain() = User(
    id = id,
    photo = photo,
    fullName = fullName,
    favoriteTourId = favoriteTourId ?: ""
)

fun UserModel.toDatabase() = UserEntity(
    id = id,
    photo = photo,
    fullName = fullName,
    email = email,
    favoriteTourId = favoriteTourId,
    lastTourIds = listOf(lastTourIds)
)