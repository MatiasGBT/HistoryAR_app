package com.grupo3.historyar.data.network.model

import com.google.gson.annotations.SerializedName
import com.grupo3.historyar.models.Subscription
import java.util.Date

data class SubscriptionModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("usuario") val idUser: String = "",
    @SerializedName("mp_id") val idMP: String = "",
    @SerializedName("precio") val price: Double = 0.0,
    @SerializedName("fechaCreacion") val creationDate: Date = Date()
)

fun SubscriptionModel.toDomain() = Subscription(
    id = id,
    idUser = idUser,
    idMP = idMP,
    price = price,
    creationDate = creationDate
)