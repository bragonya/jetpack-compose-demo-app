package com.bragonya.unsplashdemoapp.di

import android.content.Context
import androidx.room.Room
import com.bragonya.unsplashdemoapp.persistency.AppDatabase
import com.bragonya.unsplashdemoapp.persistency.FavoritesDAO
import com.bragonya.unsplashdemoapp.persistency.FavoritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "images-database"
    ).build()

    @Provides
    fun provideFavoritesDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FavoritesDataBase::class.java, "favorite-images-database"
    ).build()
}