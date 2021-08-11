package com.bragonya.unsplashdemoapp.models

import androidx.room.Entity

@Entity(tableName = "profile_image")
data class ProfileImage(
    val small: String
)
