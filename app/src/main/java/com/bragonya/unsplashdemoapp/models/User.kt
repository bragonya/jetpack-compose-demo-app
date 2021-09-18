package com.bragonya.unsplashdemoapp.models

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import javax.annotation.Nullable

@Entity(tableName = "users")
data class User(
    val username: String,
    val name: String,
    @Embedded @SerializedName("profile_image") val profileImage: ProfileImage,
    @Nullable @SerializedName("portfolio_url") val portfolioUrl: String?,
    @Nullable @SerializedName("twitter_username") val twitterUserName: String?,
    @Nullable @SerializedName("instagram_username") val instagramUserName: String?,
)
