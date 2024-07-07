package com.grupo3.historyar.models

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.data.network.model.UserModel

data class User(
    val id: String,
    val photo: String = "",
    val fullName: String = "",
    val email: String = "",
    val favoriteTourId: String = "1",
    val lastTourIds: List<String> = listOf("1", "2", "3")
)

fun User.toModel() = UserModel(
    id = id,
    photo = photo,
    fullName = fullName,
    email = email,
    lastTourIds = lastTourIds.first()
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

fun FirebaseAuth.toModel() = User (
    id = "",
    photo = currentUser?.photoUrl?.toString() ?: "",
    fullName = currentUser?.displayName ?: "",
    email = currentUser?.email ?: ""
)