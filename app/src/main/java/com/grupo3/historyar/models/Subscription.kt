package com.grupo3.historyar.models

import java.util.Date

data class Subscription(
    val id: String,
    val idUser: String = "",
    val idMP: String = "",
    val price: Double = 0.0,
    val creationDate: Date = Date(),
    var isSubValid: Boolean = false,
    var daysUntilMonth: Long = 0
)

