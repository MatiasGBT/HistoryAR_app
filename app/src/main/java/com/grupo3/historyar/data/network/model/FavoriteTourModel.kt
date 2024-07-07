package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.data.database.entities.TourEntity
import com.grupo3.historyar.models.Tour

data class FavoriteTourModel(
    @SerializedName("recorridoFavorito") val favoriteTourId: String = ""
)
