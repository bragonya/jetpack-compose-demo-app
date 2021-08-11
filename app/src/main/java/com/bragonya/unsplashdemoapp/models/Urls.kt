package com.bragonya.unsplashdemoapp.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "urls")
data class Urls(
    @SerializedName("regular") val regularImage: String,
    @SerializedName("small") val smallImage: String,
    @SerializedName("thumb") val thumbImage: String,
)