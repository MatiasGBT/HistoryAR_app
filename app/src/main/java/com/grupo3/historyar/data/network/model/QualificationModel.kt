package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.models.Qualification
import com.grupo3.historyar.models.User

data class QualificationModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("usuario") val idUser: String = "",
    @SerializedName("recorrido") val idTour: String = "",
    @SerializedName("comentario") val comment: String = "",
    @SerializedName("puntuacion") val score: Int = 0
)

fun QualificationModel.toDomain(user: User) = Qualification(
    id = id,
    user = user,
    tour = idTour,
    comment = comment,
    score = score
)