package com.bragonya.unsplashdemoapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys (
    @PrimaryKey
    val imageId: String,
    val prevKey: Int?,
    val nextKey: Int?
)