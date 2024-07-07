package com.grupo3.historyar.models

data class Tour(
    val id: String,
    val name: String,
    val description: String,
    val duration: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    var isFavorite: Boolean,
    val points: List<PointOfInterest>
)