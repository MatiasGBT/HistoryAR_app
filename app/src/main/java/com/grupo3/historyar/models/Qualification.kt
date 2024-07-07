package com.grupo3.historyar.models

import com.grupo3.historyar.data.network.model.QualificationModel


data class Qualification(
    val id: String = "",
    val user: User,
    val tour: String,
    val comment: String,
    val score: Int
)

fun Qualification.toModel() = QualificationModel(id = id, comment = comment, score = score, idUser = user.id, idTour = tour)