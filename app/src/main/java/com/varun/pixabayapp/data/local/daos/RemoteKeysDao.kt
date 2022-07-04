package com.varun.pixabayapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varun.pixabayapp.data.local.entities.RemoteKeys


@Dao
interface RemoteKeysDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>): List<Long>

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysImageId(id: Int): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}