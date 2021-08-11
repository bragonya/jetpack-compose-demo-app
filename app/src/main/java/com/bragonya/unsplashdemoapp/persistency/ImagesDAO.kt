package com.bragonya.unsplashdemoapp.persistency

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bragonya.unsplashdemoapp.models.ImageRoot
import kotlinx.coroutines.flow.Flow


@Dao
interface ImagesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageRoot>)

    @Query("SELECT * FROM image_root")
    fun observeNewsPaginated(): PagingSource<Int, ImageRoot>

    @Query("DELETE FROM image_root")
    suspend fun clearAll()
}