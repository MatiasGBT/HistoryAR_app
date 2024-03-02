package com.grupo3.historyar.models


data class Qualification(
    val id: String = "",
    val user: User,
    val tour: Tour,
    val comment: String,
    val score: Int
)
