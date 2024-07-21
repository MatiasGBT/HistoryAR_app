package com.grupo3.historyar.models

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.data.network.model.UserModel

data class User(
    val id: String,
    val photo: String = "",
    val fullName: String = "",
    val email: String = "",
    var favoriteTourId: String = "",
    var lastTourIds: List<String> = emptyList(),
    var isActive: Boolean = true
)

fun User.toModel() = UserModel(
    id = id,
    photo = photo,
    fullName = fullName,
    email = email,
    isActive = isActive
)

fun User.toDatabase() = UserEntity(
    id = id,
    photo = photo,
    fullName = fullName,
    email = email,
    favoriteTourId = favoriteTourId,
    lastTourIds = lastTourIds
)

fun GoogleSignInAccount.toDomain() = User(
    id = id.orEmpty(),
    photo = photoUrl.toString(),
    fullName = displayName.orEmpty(),
    email = email.orEmpty()
)