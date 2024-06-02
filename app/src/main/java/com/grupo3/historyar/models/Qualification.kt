package com.grupo3.historyar.models


data class Qualification(
    val id: String = "",
    val user: User,
    val tour: String,
    val comment: String,
    val score: Int
)
