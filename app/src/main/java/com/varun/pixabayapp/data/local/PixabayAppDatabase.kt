package com.varun.pixabayapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.varun.pixabayapp.data.local.daos.ImageDao
import com.varun.pixabayapp.data.local.daos.RemoteKeysDao
import com.varun.pixabayapp.data.local.entities.Image
import com.varun.pixabayapp.data.local.entities.RemoteKeys


@Database(entities = [Image::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class PixabayAppDatabase : RoomDatabase() {

    abstract fun getImageDao(): ImageDao
    abstract fun getRemoteKeyDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: PixabayAppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): PixabayAppDatabase {
            return Room.databaseBuilder(context.applicationContext, PixabayAppDatabase::class.java, "Pixabay.db").build()
        }
    }
}