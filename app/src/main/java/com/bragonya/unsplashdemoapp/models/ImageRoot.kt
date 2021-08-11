package com.bragonya.unsplashdemoapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image_root")
data class ImageRoot(
    @PrimaryKey
    val id: String,
    val color: String,
    val description: String?,
    @SerializedName("alt_description") val altDescription: String?,
    @Embedded val urls: Urls,
    val likes: Int,
    @Embedded val user: User,
)