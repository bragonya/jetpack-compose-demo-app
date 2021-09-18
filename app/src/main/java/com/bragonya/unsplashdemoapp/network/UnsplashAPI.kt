package com.bragonya.unsplashdemoapp.network

import com.bragonya.unsplashdemoapp.common.DEFAULT_PAGE_SIZE
import com.bragonya.unsplashdemoapp.models.ImageRoot
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {

    @GET("/photos")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageRoot>

    @GET("/photos/{imageId}")
    suspend fun getImage(
        @Path("imageId", encoded = false) imageId: String,
    ): ImageRoot
}