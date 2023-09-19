package com.grupo3.historyar.models

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.grupo3.historyar.data.database.entities.UserEntity

data class User(
    val id: String,
    val photo: String,
    val fullName: String,
    //val favoriteTour: Tour,
    //val lastTour: Tour
)

fun UserEntity.toDomain() = User(id = id, photo = photo, fullName = fullName)
fun GoogleSignInAccount.toDomain() = User(id = id.orEmpty(), photo = photoUrl.toString(), fullName = displayName.orEmpty())