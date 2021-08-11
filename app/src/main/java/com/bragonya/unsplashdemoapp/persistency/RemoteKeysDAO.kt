package com.bragonya.unsplashdemoapp.persistency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bragonya.unsplashdemoapp.models.RemoteKeys

@Dao
interface RemoteKeysDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)
    @Query("SELECT * FROM remote_keys WHERE imageId = :imageId")
    fun remoteKeysByNewsId(imageId: String): RemoteKeys?
    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()
}