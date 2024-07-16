package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName

data class FavoriteTourModel(
    @SerializedName("recorridoFavorito") val favoriteTourId: String = ""
)
