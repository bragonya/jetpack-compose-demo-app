package com.bragonya.unsplashdemoapp.models

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    val username: String,
    val name: String,
    @Embedded @SerializedName("profile_image") val profileImage: ProfileImage,
)
