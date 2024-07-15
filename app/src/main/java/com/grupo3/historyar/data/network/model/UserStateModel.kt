package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName

data class UserStateModel(
    @SerializedName("activo") var isActive: Boolean
)
