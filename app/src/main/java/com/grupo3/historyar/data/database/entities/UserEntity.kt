package com.grupo3.historyar.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.grupo3.historyar.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "photo") val photo: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "favorite_tour") val favoriteTourId: String?,
    @ColumnInfo(name = "last_tours") val lastTourIds: List<String>?
)

fun UserEntity.toDomain() = User(id = id, photo = photo, fullName = fullName, email = email, favoriteTourId = favoriteTourId.orEmpty(), lastTourIds = lastTourIds.orEmpty())
