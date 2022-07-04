package com.varun.pixabayapp.data.remote.di

import com.varun.pixabayapp.BuildConfig
import com.varun.pixabayapp.data.remote.Http
import com.varun.pixabayapp.data.remote.apis.PixabayImageApi
import com.varun.pixabayapp.data.remote.repositories.RemoteImageRepositoryImpl
import com.varun.pixabayapp.domain.repositories.RemoteImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Http.getRetrofitClient("${BuildConfig.BASE_URL}/api/")
    }

    @Provides
    @Singleton
    fun provideImageRepository(retrofit: Retrofit): RemoteImageRepository {
        val imageApi = retrofit.create(PixabayImageApi::class.java)
        return RemoteImageRepositoryImpl(imageApi, BuildConfig.PIXABAY_API_KEY)
    }
}