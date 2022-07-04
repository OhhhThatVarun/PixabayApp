package com.varun.pixabayapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varun.pixabayapp.data.local.entities.Image


@Dao
interface ImageDao {

    @Insert
    suspend fun insert(image: Image)

    @Query("SELECT * FROM image_table where id= :id")
    suspend fun getImage(id: Int): Image?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<Image>)

    @Query("DELETE FROM image_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM image_table")
    fun getPagingSource(): PagingSource<Int, Image>
}