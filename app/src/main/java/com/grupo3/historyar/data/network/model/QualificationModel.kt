package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.models.Qualification

data class QualificationModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("usuario") val user: UserModel = UserModel(),
    @SerializedName("recorrido") val tour: TourModel = TourModel(),
    @SerializedName("comentario") val comment: String = "",
    @SerializedName("puntuacion") val score: Int = 0
)

fun QualificationModel.toDomain() = Qualification(
    id = id,
    user = user.toDomain(),
    tour = tour.toDomain(false),
    comment = comment,
    score = score
)