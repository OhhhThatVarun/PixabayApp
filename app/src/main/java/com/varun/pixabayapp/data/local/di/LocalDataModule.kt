package com.varun.pixabayapp.data.local.di

import android.app.Application
import com.varun.pixabayapp.data.local.PixabayAppDatabase
import com.varun.pixabayapp.data.local.daos.ImageDao
import com.varun.pixabayapp.data.local.repositories.LocalImageRepositoryImpl
import com.varun.pixabayapp.domain.repositories.LocalImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): PixabayAppDatabase {
        return PixabayAppDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun providesImageDao(db: PixabayAppDatabase): ImageDao {
        return db.getImageDao()
    }

    @Provides
    @Singleton
    fun providesLocalImageRepository(dao: ImageDao): LocalImageRepository {
        return LocalImageRepositoryImpl(dao)
    }
}