package com.bragonya.unsplashdemoapp.persistency

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.models.RemoteKeys

@Database(entities = arrayOf(ImageRoot::class), version = 1)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDAO
}