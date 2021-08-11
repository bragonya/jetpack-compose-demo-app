package com.bragonya.unsplashdemoapp.di

import com.bragonya.unsplashdemoapp.network.UnsplashAPI
import com.bragonya.unsplashdemoapp.persistency.AppDatabase
import com.bragonya.unsplashdemoapp.persistency.FavoritesDataBase
import com.bragonya.unsplashdemoapp.repositories.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelsModule {

    @Provides
    @ViewModelScoped
    fun providesUnsplashRepository(api: UnsplashAPI, database: AppDatabase, favoritesDataBase: FavoritesDataBase) = UnsplashRepository(database, api, favoritesDataBase)
}