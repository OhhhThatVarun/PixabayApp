package com.varun.pixabayapp.data.remote.apis

import com.varun.pixabayapp.data.remote.dtos.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface PixabayImageApi {

    @GET("?image_type=photo")
    suspend fun search(@Query("q") query: String, @Query("page") page: Int?, @Query("per_page") perPage: Int, @Query("key") key: String): SearchResponse
}